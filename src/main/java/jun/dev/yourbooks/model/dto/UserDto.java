package jun.dev.yourbooks.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long id;
    String name;
    String surname;
    String email;
    String avatarUrl;
}
