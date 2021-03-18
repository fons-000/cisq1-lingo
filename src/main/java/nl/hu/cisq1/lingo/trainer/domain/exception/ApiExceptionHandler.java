/*-- ------------------------------------------------------------------------
        -- (c) Amigoscode
        -- SPRING BOOT 2 | How To Handle Exceptions
        -- https://youtu.be/PzK4ZXa2Tbc
        --
        -- De onderstande code (ofwel de code uit de map exception) is gehaald uit de video van Amigoscode.
        -- Met de code uit deze map, kan je custom exceptions maken en teruggeven aan de client.
        -- Ik throw regelmatig ApiRequestException in m'n BlackjackController met een custom message.
-- ------------------------------------------------------------------------*/

package nl.hu.cisq1.lingo.trainer.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        //1. Create payload containing exception details
        HttpStatus httpStatus = e.getStatus();

        ApiException apiException =
                new ApiException(e.getMessage(),
                        httpStatus,
                        ZonedDateTime.now(ZoneId.of("Z")));
        //2. Return response entity
        return new ResponseEntity<>(apiException, httpStatus);
    }
}
