package jun.dev.yourbooks.model.dto;

import jun.dev.yourbooks.model.entity.Book;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.model.enums.Tag;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogDto {
    Long id;
    String text;
    Tag tag;
    BookDto book;
    double rate;
    LocalDate createdTime;
}
