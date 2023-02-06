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
@Table(name = "activation_tokens")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActivationToken {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name",nullable = false)
    String name;
    @Column(name = "surname",nullable = false)
    String surname;
    @Column(name = "email",nullable = false)
    String email;
    @Column(name = "password",nullable = false)
    String password;
    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

}
