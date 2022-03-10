package kg.itschool.sellservice.sellservice.models.dtos.operationsandpayments;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForInputDataFromUserToOperations {
    String barcode;
    int quantity;
}
