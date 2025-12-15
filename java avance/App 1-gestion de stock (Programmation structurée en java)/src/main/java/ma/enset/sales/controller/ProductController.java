package ma.enset.sales.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.enset.sales.dto.ProductDto;
import ma.enset.sales.dto.ProductRequestDto;
import ma.enset.sales.exceptions.APIErrorException;
import ma.enset.sales.services.product.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Product management API")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @GetMapping("/{uuid}")
    @Operation(summary = "Get product by UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDto> getProduct(@PathVariable String uuid) throws APIErrorException {
        return ResponseEntity.ok(productService.getProductByUuid(uuid));
    }

    @PostMapping
    @Operation(summary = "Create new product")
    @ApiResponse(responseCode = "201", description = "Product created")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductRequestDto productDto) {
        return ResponseEntity.ok(productService.createProduct(productDto));
    }

    @GetMapping
    @Operation(summary = "Get all products")
    @ApiResponse(responseCode = "200", description = "Products retrieved")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PutMapping("/{uuid}")
    @Operation(summary = "Update product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String uuid,
                                                    @RequestBody ProductDto productDto)
            throws APIErrorException {
        productDto.setUuid(uuid);
        return ResponseEntity.ok(productService.updateProduct(productDto));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete product")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable String uuid) throws APIErrorException {
        productService.deleteProduct(uuid);
        return ResponseEntity.noContent().build();
    }
}
