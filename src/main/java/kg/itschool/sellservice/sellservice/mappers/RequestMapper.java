package kg.itschool.sellservice.sellservice.mappers;

import kg.itschool.sellservice.sellservice.models.dtos.RequestsDTOS.RequestDTO;
import kg.itschool.sellservice.sellservice.models.entities.Request;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface RequestMapper {
    RequestMapper INSTANCE = Mappers.getMapper(RequestMapper.class);
    Request toRequest(RequestDTO requestDTO);
    RequestDTO toRequestDTO(Request request);
}
