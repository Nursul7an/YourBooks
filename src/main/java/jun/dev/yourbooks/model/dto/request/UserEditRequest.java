package jun.dev.yourbooks.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotNull
    MultipartFile imageFile;
}
