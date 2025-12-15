package ma.enset.sales.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ProductRequestDto")
public class ProductRequestDto {

    @JsonProperty("name")
    @Schema(name = "name", example = "Produit A")
    private String name;

    @JsonProperty("price")
    @Schema(name = "price", example = "199.99")
    private Double price;

    @JsonProperty("stock")
    @Schema(name = "stock", example = "100")
    private Integer stock;

    @JsonProperty("category")
    @Schema(name = "category", example = "Électronique")
    private String category;
}
