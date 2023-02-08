package jun.dev.yourbooks.model.wraper.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEditRequest {
    @NotBlank
    String name;
    @NotBlank
    String surname;
    @NotBlank
    String email;
    @NotBlank
    String password;

    String imageUrl;
}
