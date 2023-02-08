package jun.dev.yourbooks.mapper;

import jun.dev.yourbooks.exception.NotFoundException;
import jun.dev.yourbooks.model.entity.ActivationToken;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.model.wraper.response.ResponseJWT;
import jun.dev.yourbooks.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;
@Component
@RequiredArgsConstructor
public class UserMapper {
    private final RoleRepo roleRepo;
    public User toUserToken(ActivationToken activationToken) {
        return User.builder()
                .name(activationToken.getName())
                .surname(activationToken.getSurname())
                .email(activationToken.getEmail())
                .password(activationToken.getPassword())
                .createdAt(LocalDateTime.now())
                .avatarUrl("anon.jpg")
                .roles(Set.of(roleRepo.findRoleByName("ROLE_USER")
                        .orElseThrow(()-> new NotFoundException("No role found with name 'ROLE_USER'"))))
                .build();
    }

    public ResponseJWT toJwt(User user, String jwt) {
        return ResponseJWT.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .tokenType("Bearer")
                .role(user.getRoles().stream()
                        .findAny()
                        .orElseThrow(() -> new NotFoundException("Role not found!"))
                        .getName())
                .token(jwt)
                .build();
    }
}
