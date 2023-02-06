package jun.dev.yourbooks.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActivationTokenDto {
    Long id;
    String name;
    String surname;
    String email;
    String password;
    LocalDateTime createdAt;
}
