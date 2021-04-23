package car.hey.listings.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeycarException extends Exception{
    String exceptionCode;
    String exceptionMessage;
}
