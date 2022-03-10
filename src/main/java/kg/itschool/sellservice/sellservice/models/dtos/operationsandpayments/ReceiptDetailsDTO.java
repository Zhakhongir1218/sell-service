package kg.itschool.sellservice.sellservice.models.dtos.operationsandpayments;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReceiptDetailsDTO {
    String name;
    String barcode;
    int quantity;
    double price;
    double discount;
    double amount;
}
