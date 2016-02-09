/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author sistemask
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Acceso no autorizado")
public class InactiveUserException extends ValidationException {

    public InactiveUserException(String message) {
        super(message);
    }
    
}
