package jun.dev.yourbooks.model.dto.request;

import jun.dev.yourbooks.model.entity.Book;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.model.enums.Tag;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogRequest {
    String text;
    Tag tag;
    Long bookId;
    double rate;
}
