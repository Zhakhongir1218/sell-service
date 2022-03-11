package kg.itschool.sellservice.sellservice.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import kg.itschool.sellservice.sellservice.mappers.OperationMapper;
import kg.itschool.sellservice.sellservice.mappers.UserMapper;
import kg.itschool.sellservice.sellservice.models.dtos.DiscountDTOS.DiscountDTO;
import kg.itschool.sellservice.sellservice.models.dtos.OperationDetailsDTOS.OperationDetailsDTO;
import kg.itschool.sellservice.sellservice.models.dtos.OperationsDTOS.OperationDTO;
import kg.itschool.sellservice.sellservice.models.dtos.PriceDTOS.PriceDTO;
import kg.itschool.sellservice.sellservice.models.dtos.ProductDTOS.ProductDTO;
import kg.itschool.sellservice.sellservice.models.dtos.UserDTOS.UserDTO;
import kg.itschool.sellservice.sellservice.models.dtos.operationsandpayments.ForInputDataFromUserToOperations;
import kg.itschool.sellservice.sellservice.models.dtos.operationsandpayments.ReceiptDTO;
import kg.itschool.sellservice.sellservice.models.dtos.operationsandpayments.ReceiptDetailsDTO;
import kg.itschool.sellservice.sellservice.models.entities.Operation;
import kg.itschool.sellservice.sellservice.repositories.OperationRepo;
import kg.itschool.sellservice.sellservice.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OperationServiceImpl implements OperationService {
    @Autowired
    private OperationRepo operationRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PriceService priceService;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private OperationDetailsService operationDetailsService;

    @Autowired
    private CodeService codeService;

    @Value("${jwtSecret}")
    private String secretKey;

    @Override
    public ResponseEntity<?> provideOperation(String token, List<ForInputDataFromUserToOperations> sellingList) {
        ResponseEntity<?> responseEntity = codeService.verifyToken(token);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity;
        }
        List<ProductDTO> productList = new ArrayList<>();
        List<OperationDetailsDTO> operationDetailDtoList = new ArrayList<>();
        List<ReceiptDetailsDTO> receiptDetailsDtoList = new ArrayList<>();
        ReceiptDTO receiptDTO = new ReceiptDTO();
        UserDTO userDTO;
        ProductDTO productDTO;
        PriceDTO price;
        DiscountDTO discount;


        double amount;
        double totalAmount = 0;

        //Проверка существуют ли продукты по баркоду
        for (ForInputDataFromUserToOperations inputData : sellingList) {
            productDTO = productService.findProductByBarcode(inputData.getBarcode());
            if (Objects.isNull(productDTO)) {
                return new ResponseEntity<>(new ErrorResponse("Некорректно введенные данный!"
                        , "Проверьте введенный штрихкод -> " + inputData.getBarcode())
                        , HttpStatus.NOT_FOUND);
            }
            OperationDetailsDTO operationDetailsDTO = new OperationDetailsDTO();
            operationDetailsDTO.setProductDTO(productDTO);
            operationDetailsDTO.setQuantity(inputData.getQuantity());
            price = priceService.getActualPrice(productDTO.getId());
            discount = discountService.getActualDiscount(productDTO.getId());
            if (Objects.isNull(discount)) {
                amount = price.getPrice() * inputData.getQuantity();
            } else {
                int tmpDiscount = (int) (price.getPrice() / discount.getDiscount());
                amount = (price.getPrice() - tmpDiscount) * inputData.getQuantity();
            }
            totalAmount += amount;

            operationDetailsDTO.setAmount(amount);


            operationDetailDtoList.add(operationDetailsDTO);

            ReceiptDetailsDTO receiptDetailsDto = new ReceiptDetailsDTO();

            receiptDetailsDto.setName(productDTO.getName());

            receiptDetailsDto.setBarcode(productDTO.getBarcode());

            receiptDetailsDto.setQuantity(inputData.getQuantity());

            receiptDetailsDto.setPrice(price.getPrice());


            if (Objects.isNull(discount)) {
                receiptDetailsDto.setDiscount(0);
            } else {
                receiptDetailsDto.setDiscount(discount.getDiscount());
            }

            receiptDetailsDto.setAmount(amount);

            receiptDetailsDtoList.add(receiptDetailsDto);

        }
        Jws<Claims> jwt = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        userDTO = codeService.findByLogin((String) jwt.getBody().get(("login")));


        receiptDTO.setCashier(userDTO.getName());

        receiptDTO.setTotalAmount(totalAmount);

        receiptDTO.setReceiptDetailsDto(receiptDetailsDtoList);

        Operation operation = new Operation();

        operation.setTotal_price(totalAmount);

        operation.setUser(UserMapper.INSTANCE.toUser(userDTO));


        operationRepo.save(operation);

        for (OperationDetailsDTO element : operationDetailDtoList) {
            OperationDTO operationDTO = new OperationDTO();
            operationDTO.setId(operation.getId());
            operationDTO.setTotal_price(operation.getTotal_price());
            operationDTO.setChange(operation.getChange());
            operationDTO.setAdd_date(operation.getAdd_date());
            operationDTO.setUserDTO(UserMapper.INSTANCE.toUserDTO(operation.getUser()));

            element.setOperationDTO(operationDTO);
        }

        operationDetailsService.saveOperationDetails(operationDetailDtoList);

        return ResponseEntity.ok(receiptDTO);

    }

    @Override
    public ResponseEntity<?> payment(String token, Long operationId, double cash) {
        ResponseEntity<?> responseEntity =
                codeService.verifyToken(token);

        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {

            return responseEntity;
        }

        Operation operation = operationRepo.findOperationById(operationId);

        if (Objects.isNull(operation)) {
            return new ResponseEntity<>(
                    new ErrorResponse("Не найдена операция!"
                            , "Вы ввели некорректный ID операции!")
                    , HttpStatus.NOT_FOUND);
        }

        double change = cash - operation.getTotal_price();

        if (change < 0) {
            return new ResponseEntity<>(
                    new ErrorResponse("Недостаточно средств для проведения операции!", null)
                    , HttpStatus.CONFLICT);
        }

        operation.setCash(cash);
        operation.setChange(change);
        operation.setAdd_date(LocalDateTime.now());
        Jws<Claims> jwt = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        UserDTO userDTO = codeService.findByLogin((String) jwt.getBody().get(("login")));
        operation.setUser(UserMapper.INSTANCE.toUser(userDTO));

        operationRepo.save(operation);

        OperationDTO operationDTO = OperationMapper.INSTANCE.toOperationDTO(operation);
        operationDTO.setUserDTO(UserMapper.INSTANCE.toUserDTO(operation.getUser()));

        return ResponseEntity.ok(operationDTO);
    }
}


