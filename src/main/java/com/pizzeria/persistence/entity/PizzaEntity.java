package com.pizzeria.persistence.entity;

import com.pizzeria.persistence.audit.AuditPizzaListener;
import com.pizzeria.persistence.audit.AuditableEntity;
import com.pizzeria.persistence.entity.converter.BooleanToSmallintConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Entity
@EntityListeners({AuditingEntityListener.class, AuditPizzaListener.class})
@Table(name = "pizza")
@Getter
@Setter
@NoArgsConstructor
public class PizzaEntity extends AuditableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pizza", nullable = false)
    private Integer idPizza;

    @Column(nullable = false, length = 30, unique = true)
    private String name;

    @Column(nullable = false, length = 150)
    private String description;

    @Column(nullable = false, columnDefinition = "Decimal(5,2)")
    private Double price;

    @Column(columnDefinition = "smallint")
    @Convert(converter = BooleanToSmallintConverter.class)
    private Boolean vegetarian;

    @Column(columnDefinition = "smallint")
    @Convert(converter = BooleanToSmallintConverter.class)
    private Boolean vegan;

    @Column(columnDefinition = "smallint", nullable = false)
    @Convert(converter = BooleanToSmallintConverter.class)
    private Boolean available;

    @Override
    public String toString() {
        return "PizzaEntity{" +
                "idPizza=" + idPizza +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", vegetarian=" + vegetarian +
                ", vegan=" + vegan +
                ", available=" + available +
                '}';
    }
}
