package kg.itschool.sellservice.sellservice.services;

import kg.itschool.sellservice.sellservice.models.dtos.OperationDetailsDTOS.OperationDetailsDTO;

import java.util.List;

public interface OperationDetailsService {
    void saveOperationDetails(List<OperationDetailsDTO> operationDetailsDtoList);
}
