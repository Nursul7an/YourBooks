package jun.dev.yourbooks.service;

import jun.dev.yourbooks.model.dto.UserDto;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.model.dto.request.LoginRequest;
import jun.dev.yourbooks.model.dto.request.RegisterRequest;
import jun.dev.yourbooks.model.dto.request.ResetPasswordRequest;
import jun.dev.yourbooks.model.dto.request.UserEditRequest;
import jun.dev.yourbooks.model.dto.response.ResponseJWT;
import org.springframework.http.ResponseEntity;

public interface UserService {
    void register(RegisterRequest request);

    void activate(String link);

    ResponseEntity<ResponseJWT> login(LoginRequest loginRequest);

    void sendLink(String email);

    void resetPassword(String link, ResetPasswordRequest request);

    UserDto edit(UserEditRequest editRequest, User user);

    UserDto findUserById(Long id);
}
