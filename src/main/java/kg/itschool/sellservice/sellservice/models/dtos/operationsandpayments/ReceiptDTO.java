package kg.itschool.sellservice.sellservice.models.dtos.operationsandpayments;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReceiptDTO {
    List<ReceiptDetailsDTO> receiptDetailsDto;
    double totalAmount;
    String cashier;
}
