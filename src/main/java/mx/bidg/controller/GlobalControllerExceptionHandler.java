/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import mx.bidg.exceptions.ValidationException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author sistemask
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Ha habido un problema "
            + "con su solicitud, intente nuevamente")
    public void exceptionHandler(Exception e) throws Exception{
        Logger.getLogger(e.getStackTrace()[0].getClassName()).log(Level.SEVERE, e.getMessage(), e);
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
            throw e;
        
    }
    
    @ExceptionHandler(ValidationException.class)
    public void validationExceptionHandler(Exception e) throws Exception {
        Logger.getLogger(e.getStackTrace()[0].getClassName()).log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }
    
}
