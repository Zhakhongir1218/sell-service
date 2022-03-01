package kg.itschool.sellservice.sellservice.mappers;

import kg.itschool.sellservice.sellservice.models.dtos.DiscountDTOS.DiscountDTO;
import kg.itschool.sellservice.sellservice.models.entities.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper
public interface DiscountMapper {
    DiscountMapper INSTANCE = Mappers.getMapper(DiscountMapper.class);

    DiscountDTO toDiscountDTO(Discount discount);
    Discount toDiscount(DiscountDTO discountDTO);
}
