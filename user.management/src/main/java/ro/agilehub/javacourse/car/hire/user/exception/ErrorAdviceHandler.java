package ro.agilehub.javacourse.car.hire.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class ErrorAdviceHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UserNotFoundException.class, EmailNotFoundException.class, UsernameNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected ErrorMessage notFound(
            UserNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(),
                new Date(), ex.getMessage(), request.getDescription(false));
        return message;
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ErrorMessage badRequest(
            IllegalArgumentException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
                new Date(), ex.getMessage(), request.getDescription(false));
        return message;
    }
}
