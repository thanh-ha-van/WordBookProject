package ha.thanh.oxfordmodule.exceptions;

public class UnsupportedLanguageException extends RuntimeException {
    public UnsupportedLanguageException() {

    }

    public UnsupportedLanguageException(String message) {
        super(message);
    }

    public UnsupportedLanguageException(Throwable cause) {
        super(cause);
    }

    public UnsupportedLanguageException(String message, Throwable cause) {
        super(message, cause);
    }
}
