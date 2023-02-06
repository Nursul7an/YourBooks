package jun.dev.yourbooks.exception;

public class AuthenticationException extends  RuntimeException{
    public AuthenticationException() {
    }
    public AuthenticationException(String message) {
        super(message);
    }
}
