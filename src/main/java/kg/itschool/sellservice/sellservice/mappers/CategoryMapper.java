package kg.itschool.sellservice.sellservice.mappers;

import kg.itschool.sellservice.sellservice.models.dtos.CategoriesDTOS.CategoryDTO;
import kg.itschool.sellservice.sellservice.models.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO toCategoryDto(Category category);
    Category toCategory(CategoryDTO categoryDTO);
}
