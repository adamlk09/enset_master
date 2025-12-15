package ma.enset.sales.mapper;

import ma.enset.sales.dto.ProductRequestDto;
import ma.enset.sales.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductRequestMapper extends GenericMapper<ProductRequestDto, Product> {

}
