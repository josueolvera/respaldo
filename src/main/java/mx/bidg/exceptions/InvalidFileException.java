package mx.bidg.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Rafael Viveros
 * Created on 12/01/16.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Tipo de archivo no admitido")
public class InvalidFileException extends ValidationException {
    public InvalidFileException() {
    }

    public InvalidFileException(String message) {
        super(message);
    }
}
