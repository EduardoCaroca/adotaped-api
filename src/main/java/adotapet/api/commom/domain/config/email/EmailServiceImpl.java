package adotapet.api.commom.domain.config.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    @Value("${spring.mail.active}")
    private boolean active;

    @Override
    public void sendEmail(String to, String subject, String text) {
        if (active) {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setFrom("adopet@email.com");
            email.setTo(to);
            email.setSubject(subject);
            email.setText(text);
            emailSender.send(email);
        }
    }
}
