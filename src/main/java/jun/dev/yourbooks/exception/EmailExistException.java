package jun.dev.yourbooks.exception;

public class EmailExistException extends RuntimeException{
    public EmailExistException() {
    }
    public EmailExistException(String message) {
        super(message);
    }

}

