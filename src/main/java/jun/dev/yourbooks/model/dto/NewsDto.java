package jun.dev.yourbooks.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsDto {
    Long id;
    String title;
    String description;
    LocalDateTime createdTime;
    String imageUrl;
}
