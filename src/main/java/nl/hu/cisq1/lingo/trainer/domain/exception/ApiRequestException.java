///*-- ------------------------------------------------------------------------
//        -- (c) Amigoscode
//        -- SPRING BOOT 2 | How To Handle Exceptions
//        -- https://youtu.be/PzK4ZXa2Tbc
//        --
//        -- De onderstande code (ofwel de code uit de map exception) is gehaald uit de video van Amigoscode.
//        -- Met de code uit deze map, kan je custom exceptions maken en teruggeven aan de client.
//        -- Ik throw regelmatig ApiRequestException in m'n BlackjackController met een custom message.
//-- ------------------------------------------------------------------------*/
//
//package nl.hu.cisq1.lingo.trainer.domain.exception;
//
//import org.springframework.http.HttpStatus;
//
//public class ApiRequestException extends RuntimeException {
//    private HttpStatus httpStatus;
//
//    public ApiRequestException(String message) {
//        super(message);
//        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//    }
//    public ApiRequestException(String message, HttpStatus httpStatus) {
//        super(message);
//        this.httpStatus = httpStatus;
//    }
//
//    public HttpStatus getStatus() {
//        return httpStatus;
//    }
//}