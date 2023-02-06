package jun.dev.yourbooks.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long id;
    @Column(name = "name",nullable = false)
    String name;
    @Column(name = "surname",nullable = false)
    String surname;
    @Column(name = "email",nullable = false)
    String email;
    @Column(name = "password",nullable = false)
    String password;
    String avatarUrl;
}
