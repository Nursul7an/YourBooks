package jun.dev.yourbooks.mapper;

import jun.dev.yourbooks.exception.NotFoundException;
import jun.dev.yourbooks.model.dto.UserDto;
import jun.dev.yourbooks.model.entity.ActivationToken;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.model.wraper.request.UserEditRequest;
import jun.dev.yourbooks.model.wraper.response.ResponseJWT;
import jun.dev.yourbooks.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;
@Component
@RequiredArgsConstructor
public class UserMapper {
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
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

    public User toUserFromEdit(UserEditRequest editRequest,String imageUrl, User user) {
        user.setName(editRequest.getName());
        user.setSurname(editRequest.getSurname());
        user.setEmail(editRequest.getEmail());
        user.setPassword(passwordEncoder.encode(editRequest.getPassword()));
        user.setAvatarUrl(imageUrl);
        return user;
    }

    public UserDto toDto(User editedUser) {
        return UserDto.builder()
                .id(editedUser.getId())
                .name(editedUser.getName())
                .surname(editedUser.getSurname())
                .email(editedUser.getEmail())
                .avatarUrl(editedUser.getAvatarUrl())
                .build();
    }
}
