package kg.itschool.sellservice.sellservice.services.impl;

import kg.itschool.sellservice.sellservice.exceptions.UserNotFoundException;
import kg.itschool.sellservice.sellservice.mappers.DiscountMapper;
import kg.itschool.sellservice.sellservice.mappers.PriceMapper;
import kg.itschool.sellservice.sellservice.models.dtos.DiscountDTOS.DiscountDTO;
import kg.itschool.sellservice.sellservice.models.entities.Discount;
import kg.itschool.sellservice.sellservice.models.entities.Price;
import kg.itschool.sellservice.sellservice.models.entities.Product;
import kg.itschool.sellservice.sellservice.repositories.DiscountRepo;
import kg.itschool.sellservice.sellservice.services.CodeService;
import kg.itschool.sellservice.sellservice.services.DiscountService;
import kg.itschool.sellservice.sellservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class DiscountServiceImpl implements DiscountService {
    @Autowired
    private CodeService codeService;
    @Autowired
    private DiscountRepo discountRepo;
    @Autowired
    private ProductService productService;

    @Override
    public ResponseEntity<?> createNewDiscount(String token, DiscountDTO discountDTO) {
        ResponseEntity<?> responseEntity = codeService.verifyToken(token);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity;
        }

        if (discountDTO.getDiscount() <= 0) {
            return new ResponseEntity<>(new UserNotFoundException("Указанная скидка не может быть ниже нуля"), HttpStatus.CONFLICT);
        }
        if (discountDTO.getDiscount() > 100) {
            return new ResponseEntity<>(new UserNotFoundException("Скидка не должна превышать 100 процентов"), HttpStatus.CONFLICT);
        }
        if (discountDTO.getEnd_date().isBefore(LocalDateTime.now())) {
            return new ResponseEntity<>(new UserNotFoundException("Крайний срок указанной скидки должен быть позднее сегодняшнего дня"), HttpStatus.CONFLICT);
        }
        if (Objects.isNull(discountDTO.getProduct())) {
            return new ResponseEntity<>(new UserNotFoundException("Вы забыли указать продукт"), HttpStatus.CONFLICT);
        }
        Discount discount = DiscountMapper.INSTANCE.toDiscount(discountDTO);
        discountRepo.save(discount);
        return ResponseEntity.ok("Вы успешно вписали новую цену");
    }

    @Override
    public DiscountDTO getActualDiscount(Long id) {
        Product product = productService.findProductById(id);
        List<Discount> discountList = discountRepo.findAllByProduct(product);
        Discount discount = discountList.stream().filter(x -> x.getStart_date()
                .isBefore(LocalDateTime.now()))
                .sorted(Comparator.comparing(Discount::getStart_date))
                .reduce(((discount1, discount2) -> discount2))
                .orElse(null);
        return DiscountMapper.INSTANCE.toDiscountDTO(discount);
    }


}
