package ha.thanh.oxfordmodule.exceptions;

public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException() {

    }

    public ServiceUnavailableException(String message) {
        super(message);
    }

    public ServiceUnavailableException(Throwable cause) {
        super(cause);
    }

    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
