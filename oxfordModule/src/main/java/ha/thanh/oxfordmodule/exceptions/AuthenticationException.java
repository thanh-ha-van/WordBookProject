package ha.thanh.oxfordmodule.exceptions;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {

    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
