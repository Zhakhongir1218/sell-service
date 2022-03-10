package kg.itschool.sellservice.sellservice.services.impl;

import kg.itschool.sellservice.sellservice.mappers.OperationDetailsMapper;
import kg.itschool.sellservice.sellservice.models.dtos.OperationDetailsDTOS.OperationDetailsDTO;
import kg.itschool.sellservice.sellservice.repositories.OperationDetailsRepo;
import kg.itschool.sellservice.sellservice.services.OperationDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OperationDetailsServiceImpl implements OperationDetailsService {
    @Autowired
    private OperationDetailsRepo operationDetailsRepo;

    @Override
    public void saveOperationDetails(List<OperationDetailsDTO> operationDetailsDtoList) {
        for (OperationDetailsDTO element: operationDetailsDtoList) {
            operationDetailsRepo.save(OperationDetailsMapper.INSTANCE.toOperationDetails(element));
        }
    }
}
