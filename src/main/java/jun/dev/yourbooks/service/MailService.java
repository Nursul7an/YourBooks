package jun.dev.yourbooks.service;

public interface MailService {
    void sendMessage(String toEmail, String subject, String text);
}
