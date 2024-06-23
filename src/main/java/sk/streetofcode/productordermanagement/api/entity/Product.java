package sk.streetofcode.productordermanagement.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @SequenceGenerator(name = "product_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String description;
    @Column
    private Long amount;
    @Column
    private double price;

    public Product (String name, String description){
        this.name = name;
        this.description = description;
    }
}
