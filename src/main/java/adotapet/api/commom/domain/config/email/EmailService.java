package adotapet.api.commom.domain.config.email;

public interface EmailService {

    void sendEmail(String to, String subject, String text);
}
