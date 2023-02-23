package jun.dev.yourbooks.model.entity;

import jun.dev.yourbooks.model.enums.Tag;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.servlet.tags.form.TagWriter;

import javax.persistence.*;
import javax.validation.constraints.Size;
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
    @Enumerated(EnumType.STRING)
    Tag tag;
    @ManyToOne
            @JoinColumn(name = "book_id")
    Book book;
    @ManyToOne
            @JoinColumn(name = "user_id")
     User publisher;
    @Size(min = 1, max = 5)
    @Column(name = "rates")
    double rate;
    @CreationTimestamp
    LocalDate createdTime;
}
