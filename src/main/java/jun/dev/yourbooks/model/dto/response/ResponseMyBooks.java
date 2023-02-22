package jun.dev.yourbooks.model.dto.response;

import jun.dev.yourbooks.model.dto.BookDto;
import jun.dev.yourbooks.model.dto.UserDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseMyBooks {
    UserDto userDto;
    List<BookDto> bookDtoList;
}
