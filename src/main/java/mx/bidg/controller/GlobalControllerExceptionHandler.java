package mx.bidg.controller;

import mx.bidg.exceptions.ValidationException;
import mx.bidg.pojos.ErrorField;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sistemask
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class, SQLException.class, RuntimeException.class})
    public ResponseEntity<Object> exceptionHandler(Exception e, WebRequest request) {
        Logger.getLogger("Exception").log(Level.SEVERE, e.getMessage(), e);
        String errorMessage = "Ha habido un problema con su solicitud, intente nuevamente";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if(e instanceof SQLIntegrityConstraintViolationException) {
            errorMessage = "Elemento duplicado";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ErrorField errors = new ErrorField(
                null, errorMessage, status.value()
        );

        return handleExceptionInternal(e, errors, headers, status, request);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> validationExceptionHandler(Exception e, WebRequest request) {
        Logger.getLogger("ValidationException").log(Level.SEVERE, e.getMessage(), e);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        ValidationException ex = (ValidationException) e;

        return handleExceptionInternal(e, ex.getError(), headers, ex.getStatus(), request);
    }
}
