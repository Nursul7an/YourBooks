package jun.dev.yourbooks.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "news")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class News {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String title;
    @Column(nullable = false)
    String description;
    @Column(nullable = false, name = "created_time")
    @CreationTimestamp
    LocalDateTime createdTime;
    String imageUrl;
}
