package kg.itschool.sellservice.sellservice.services.impl;

import kg.itschool.sellservice.sellservice.exceptions.UserNotFoundException;
import kg.itschool.sellservice.sellservice.mappers.PriceMapper;
import kg.itschool.sellservice.sellservice.mappers.ProductMapper;
import kg.itschool.sellservice.sellservice.models.dtos.PriceDTOS.PriceDTO;
import kg.itschool.sellservice.sellservice.models.dtos.ProductDTOS.ProductDTO;
import kg.itschool.sellservice.sellservice.models.entities.Price;
import kg.itschool.sellservice.sellservice.models.entities.Product;
import kg.itschool.sellservice.sellservice.repositories.PriceRepo;
import kg.itschool.sellservice.sellservice.services.CodeService;
import kg.itschool.sellservice.sellservice.services.PriceService;
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
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceRepo priceRepo;
    @Autowired
    private CodeService codeService;
    @Autowired
    private ProductService productService;

    @Override
    public ResponseEntity<?> createNewPrice(String token, PriceDTO priceDTO) {
        ResponseEntity<?> responseEntity = codeService.verifyToken(token);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity;
        }

        if (priceDTO.getPrice() <= 0) {
            return new ResponseEntity<>(new UserNotFoundException("Указанная цена не может быть ниже нуля!"), HttpStatus.CONFLICT);
        }
        if (priceDTO.getEnd_date().isBefore(LocalDateTime.now())) {
            return new ResponseEntity<>(new UserNotFoundException("Крайний срок указанной цены должен быть позднее сегодняшнего дня"), HttpStatus.CONFLICT);
        }
        if (Objects.isNull(priceDTO.getProduct())) {
            return new ResponseEntity<>(new UserNotFoundException("Вы забыли указать продукт"), HttpStatus.CONFLICT);
        }
        Price price = PriceMapper.INSTANCE.toPrice(priceDTO);
        priceRepo.save(price);
        return ResponseEntity.ok("Вы успешно вписали новую цену");
    }

    @Override
    public PriceDTO getActualPrice(Long id) {
        Product product = productService.findProductById(id);
        List<Price> priceList = priceRepo.findAllByProduct(product);
        Price price = priceList.stream().filter(x -> x.getStart_date()
                .isBefore(LocalDateTime.now()))
                .sorted(Comparator.comparing(Price::getStart_date))
                .reduce(((price1, price2) -> price2))
                .orElse(null);
        return PriceMapper.INSTANCE.toPriceDTO(price);
    }

}
