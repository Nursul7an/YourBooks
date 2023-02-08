package jun.dev.yourbooks.mapper;

import jun.dev.yourbooks.model.entity.ActivationToken;
import jun.dev.yourbooks.model.wraper.RegisterRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
@Component
public class ActivationTokenMapper {
    public ActivationToken toEntity(RegisterRequest request) {
        return  ActivationToken.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(request.getPassword())
                .createdAt(LocalDateTime.now())
                .link(UUID.randomUUID().toString())
                .build();
    }
}
