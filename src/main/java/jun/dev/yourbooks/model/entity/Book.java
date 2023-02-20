package jun.dev.yourbooks.model.entity;

import jun.dev.yourbooks.model.enums.Style;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "books")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name", nullable = false)
    String name;
    @Column(name = "style", nullable = false)
    @Enumerated(EnumType.STRING)
    Style style;
    @Column(name = "descriptions", nullable = false)
    String description;
    @Column(name = "author", nullable = false)
    String author;
    @ManyToOne
    User publisher;
    @Column(name = "image_url", nullable = false)
    String imageUrl;
    @Column(name = "book_url", nullable = false)
    String bookUrl;
    @Column(name = "rating", columnDefinition = "0")
    double rating;
    @Column(name = "published-year", nullable = false)
    LocalDateTime publishedYear;
}
