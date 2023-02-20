package jun.dev.yourbooks.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jun.dev.yourbooks.model.enums.Style;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

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
    @Column(name = "rating")
    double rating;
    @Column(name = "published_year",nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDate publishedYear;
    @CreationTimestamp
    LocalDate createdTime;
}
