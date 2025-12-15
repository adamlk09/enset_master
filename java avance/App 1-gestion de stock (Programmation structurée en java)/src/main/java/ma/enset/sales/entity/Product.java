package ma.enset.sales.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "product")
@NoArgsConstructor
public class Product extends BaseModel {

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stock;

    @Column(length = 50)
    private String category;

    @Column(length = 50)
    private String name;
}
