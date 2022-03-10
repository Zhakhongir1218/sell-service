package kg.itschool.sellservice.sellservice.mappers;

import kg.itschool.sellservice.sellservice.models.dtos.OperationDetailsDTOS.OperationDetailsDTO;
import kg.itschool.sellservice.sellservice.models.entities.OperationDetails;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OperationDetailsMapper {
    OperationDetailsMapper INSTANCE = Mappers.getMapper(OperationDetailsMapper.class);

    OperationDetailsDTO toOperationDetailsDTO(OperationDetails operationDetails);

    OperationDetails toOperationDetails(OperationDetailsDTO operationDetailsDTO);


}
