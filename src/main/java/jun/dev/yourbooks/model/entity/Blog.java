package jun.dev.yourbooks.model.entity;

import jun.dev.yourbooks.model.enums.Tag;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.servlet.tags.form.TagWriter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "blogs")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Blog {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String text;
    Tag tag;
    @ManyToOne
    Book book;
    double rate;
    @CreationTimestamp
    LocalDate createdTime;
}
