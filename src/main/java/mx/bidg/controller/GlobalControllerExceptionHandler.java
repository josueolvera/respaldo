package mx.bidg.controller;

import mx.bidg.exceptions.ValidationException;
import mx.bidg.pojos.ErrorField;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.*;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Set;
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

        if (e instanceof SQLIntegrityConstraintViolationException || e instanceof ConstraintViolationException) {
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

    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        pageNotFoundLogger.warn(ex.getMessage());
        status = HttpStatus.METHOD_NOT_ALLOWED;

        Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
        if (!supportedMethods.isEmpty()) {
            headers.setAllow(supportedMethods);
        }
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ErrorField errors = new ErrorField(
                null, "Método no autorizado", status.value()
        );

        return handleExceptionInternal(ex, errors, headers, status, request);
    }
}
