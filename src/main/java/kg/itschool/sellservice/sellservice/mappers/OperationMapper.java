package kg.itschool.sellservice.sellservice.mappers;

import kg.itschool.sellservice.sellservice.models.dtos.OperationsDTOS.OperationDTO;
import kg.itschool.sellservice.sellservice.models.entities.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OperationMapper {
    OperationMapper INSTANCE = Mappers.getMapper(OperationMapper.class);
    Operation toOperation(OperationDTO operationDTO);
    OperationDTO toOperationDTO(Operation operation);

}