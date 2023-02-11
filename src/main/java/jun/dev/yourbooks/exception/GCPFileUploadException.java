package jun.dev.yourbooks.exception;

public class GCPFileUploadException extends RuntimeException{
    public GCPFileUploadException() {
    }
    public GCPFileUploadException(String message) {
        super(message);
    }
}
