/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.exceptions;

import mx.bidg.pojos.ErrorField;
import org.springframework.http.HttpStatus;

/**
 *
 * @author sistemask
 */
public class ValidationException extends RuntimeException {

    private HttpStatus status;
    private String loggerMessage;
    private String clientMessage;
    private ErrorField errorField;

    public ValidationException() {

    }

    public ValidationException(String message) {
        super(message);
        this.loggerMessage = message;
    }

    public ValidationException(String loggerMessage, String clientMessage) {
        super(loggerMessage);
        this.loggerMessage = loggerMessage;
        this.clientMessage = clientMessage;
        this.status = HttpStatus.BAD_REQUEST;
        ErrorField errorField = new ErrorField(clientMessage);
        errorField.getError().setCode(status.value());
        this.errorField = errorField;
    }

    public ValidationException(String loggerMessage, String clientMessage, HttpStatus status) {
        super(loggerMessage);
        this.loggerMessage = loggerMessage;
        this.clientMessage = clientMessage;
        this.status = status;
        ErrorField errorField = new ErrorField(clientMessage);
        errorField.getError().setCode(status.value());
        this.errorField = errorField;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getLoggerMessage() {
        return loggerMessage;
    }

    public String getClientMessage() {
        return clientMessage;
    }

    public ErrorField getError() {
        return errorField;
    }
}
