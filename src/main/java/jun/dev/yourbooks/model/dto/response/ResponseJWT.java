package jun.dev.yourbooks.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseJWT {
    String name;
    String surname;
    String email;
    String tokenType;
    String role;
    String token;
}
