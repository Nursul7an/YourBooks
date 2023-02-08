package jun.dev.yourbooks.model.entity;

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
@Table(name = "reset_passwords")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPassword {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    User user;
    @Column(name = "link", nullable = false)
    String link;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
