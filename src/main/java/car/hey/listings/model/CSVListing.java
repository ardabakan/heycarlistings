package car.hey.listings.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CSVListing {

    @CsvBindByPosition(position = 0)
    private String code;
    @CsvBindByPosition(position = 1)
    private String makeModel;
    @CsvBindByPosition(position = 2)
    private Float powerInPs;
    @CsvBindByPosition(position = 3)
    private Integer year;
    @CsvBindByPosition(position = 4)
    private String color;
    @CsvBindByPosition(position = 5)
    private Float price;
}
