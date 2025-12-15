package ma.enset.sales.services.product;

import lombok.RequiredArgsConstructor;
import ma.enset.sales.dto.ProductDto;
import ma.enset.sales.dto.ProductRequestDto;
import ma.enset.sales.entity.Product;
import ma.enset.sales.exceptions.APIErrorException;
import ma.enset.sales.exceptions.ErrorCode;
import ma.enset.sales.exceptions.NotFoundIdException;
import ma.enset.sales.mapper.ProductMapper;
import ma.enset.sales.mapper.ProductRequestMapper;
import ma.enset.sales.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductRequestMapper productRequestMapper;

    @Override
    public ProductDto getProductByUuid(String uuid) throws APIErrorException {
        return productMapper.toDto(getProduct(uuid));
    }

    @Override
    public ProductDto createProduct(ProductRequestDto productDto) {
        Product product = productRequestMapper.toEntity(productDto);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return productMapper.toDtoList(products);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) throws APIErrorException {
        Product product = getProduct(productDto.getUuid());
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        product.setCategory(productDto.getCategory());
        product.setStock(productDto.getStock());
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public void deleteProduct(String uuid) throws APIErrorException {
        Product product = getProduct(uuid);
        productRepository.delete(product);
    }

    @Override
    public Product getProduct(String uuid) throws APIErrorException {
        return productRepository.findByUuid(UUID.fromString(uuid)).orElseThrow(
                () -> new NotFoundIdException(ErrorCode.E0021)
        );
    }
}
