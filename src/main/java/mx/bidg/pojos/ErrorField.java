package mx.bidg.pojos;

/**
 * @author Rafael Viveros
 * Created on 21/01/16.
 */
public class ErrorField {

    private Error error;

    public ErrorField(String message) {
        this.error = new Error();
        error.setMessage(message);
    }

    public ErrorField(String field, String message, Integer code) {
        this.error = new Error(field, message, code);
    }

    public Error getError() {
        return error;
    }

    public static class Error {

        private String field;
        private String message;
        private Integer code;

        public Error() {}

        public Error(String field, String message, Integer code) {
            this.field = field;
            this.message = message;
            this.code = code;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }
    }
}
