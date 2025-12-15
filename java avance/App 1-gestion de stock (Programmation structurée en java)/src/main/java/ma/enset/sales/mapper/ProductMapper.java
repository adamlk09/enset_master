package ma.enset.sales.mapper;

import ma.enset.sales.dto.ProductDto;
import ma.enset.sales.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends GenericMapper<ProductDto, Product> {

}
