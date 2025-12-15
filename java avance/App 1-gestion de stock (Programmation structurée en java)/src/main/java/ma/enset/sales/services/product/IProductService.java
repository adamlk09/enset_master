package ma.enset.sales.services.product;

import ma.enset.sales.dto.ProductDto;
import ma.enset.sales.dto.ProductRequestDto;
import ma.enset.sales.entity.Product;
import ma.enset.sales.exceptions.APIErrorException;

import java.util.List;

public interface IProductService {

    ProductDto getProductByUuid(String uuid) throws APIErrorException;

    ProductDto createProduct(ProductRequestDto productDto);

    List<ProductDto> getAllProducts();

    ProductDto updateProduct(ProductDto productDto) throws APIErrorException;

    void deleteProduct(String uuid) throws APIErrorException;

    Product getProduct(String uuid) throws APIErrorException;
}
