package car.hey.listings.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //@todo increase handlers as many as we can

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ExceptionResponse handleConflicts(HeycarException exception) {
        log.error("There is a conflict in your request : " + exception.getExceptionMessage());
        return new ExceptionResponse(exception.getExceptionCode(),
                exception.getExceptionMessage());
    }
}
