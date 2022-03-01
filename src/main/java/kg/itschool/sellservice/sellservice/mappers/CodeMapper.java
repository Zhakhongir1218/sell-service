package kg.itschool.sellservice.sellservice.mappers;

import kg.itschool.sellservice.sellservice.models.dtos.CodesDTOS.CodeDTO;
import kg.itschool.sellservice.sellservice.models.entities.Code;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CodeMapper {
    CodeMapper INSTANCE = Mappers.getMapper(CodeMapper.class);

    CodeDTO toCodeDTO(Code code);

    Code toCode(CodeDTO codeDTO);
}
