package jun.dev.yourbooks.exception;

public class ExpiredException extends RuntimeException{
    public ExpiredException() {
    }
    public ExpiredException(String message) {
        super(message);
    }
}
