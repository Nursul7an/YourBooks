package jun.dev.yourbooks.model.dto.request;

import jun.dev.yourbooks.model.enums.Style;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequest {
    @NotBlank
    String name;
    @NotBlank
    Style style;
    @NotBlank
    String author;
    @NonNull
    LocalDateTime publishedDate;
    @NotBlank
    String description;
    @NonNull
    MultipartFile image;
    @NonNull
    MultipartFile book;

}
