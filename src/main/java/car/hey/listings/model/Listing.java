package car.hey.listings.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Listing {

    @Id
    @GeneratedValue
    private Long id;

    private Integer dealer;
    private String code;
    private String make;
    private String model;
    private Float powerInHP;
    private Float powerInKW;
    private Integer year;
    private String color;
    private Float price;

}
