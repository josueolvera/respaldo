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
@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = "El usuario ya tiene una sesion activa")
public class ActiveSessionException extends ValidationException {
    
    public ActiveSessionException() {
        
    }
    
    public ActiveSessionException(String message) {
        super(message);
    }
    
}
