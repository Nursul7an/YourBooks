package jun.dev.yourbooks.model.wraper.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {
    @NotBlank
    String name;
    @NotBlank
    String surname;
    @NotBlank
    @Size(min = 5, max = 50)
    String password;
    @NotBlank
    String email;

}
