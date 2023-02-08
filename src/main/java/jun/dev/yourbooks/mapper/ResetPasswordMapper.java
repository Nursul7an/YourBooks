package jun.dev.yourbooks.mapper;

import jun.dev.yourbooks.model.entity.ResetPassword;
import jun.dev.yourbooks.model.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ResetPasswordMapper {
    public ResetPassword toResetPassword(User user) {
        return ResetPassword.builder()
                .link(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
    }
}
