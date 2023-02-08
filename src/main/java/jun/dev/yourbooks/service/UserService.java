package jun.dev.yourbooks.service;

import jun.dev.yourbooks.model.wraper.LoginRequest;
import jun.dev.yourbooks.model.wraper.RegisterRequest;
import jun.dev.yourbooks.model.wraper.ResetPasswordRequest;
import jun.dev.yourbooks.model.wraper.response.ResponseJWT;
import org.springframework.http.ResponseEntity;

public interface UserService {
    void register(RegisterRequest request);

    void activate(String link);

    ResponseEntity<ResponseJWT> login(LoginRequest loginRequest);

    void sendLink(String email);

    void resetPassword(String link, ResetPasswordRequest request);
}
