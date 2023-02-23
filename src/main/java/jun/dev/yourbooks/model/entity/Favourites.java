package jun.dev.yourbooks.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "favourites")
public class Favourites {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
            @JoinColumn(name = "user_id")
    User user;
    @ManyToOne
            @JoinColumn(name = "book_id")
    Book book;
}
