package ha.thanh.oxfordmodule.exceptions;

public class WordNotFoundException extends RuntimeException {
    public WordNotFoundException() {

    }

    public WordNotFoundException(String message) {
        super(message);
    }

    public WordNotFoundException(Throwable cause) {
        super(cause);
    }

    public WordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
