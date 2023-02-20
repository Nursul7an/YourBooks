package jun.dev.yourbooks.model.dto;

import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.model.enums.Style;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDto {
    Long id;
    String name;
    Style style;
    String description;
    String author;
    UserDto publisherDto;
    String imageUrl;
    String bookUrl;
    double rating;
    LocalDateTime publishedYear;
}
