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

import java.time.ZonedDateTime;

public class ApiException {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

    public ApiException(String message,
                        HttpStatus httpStatus,
                        ZonedDateTime timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}