package jun.dev.yourbooks.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class FavouritesDto {
    Long id;
    BookDto bookDto;
}
