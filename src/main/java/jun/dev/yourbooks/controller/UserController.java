package jun.dev.yourbooks.controller;

import jun.dev.yourbooks.model.dto.UserDto;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.model.wraper.request.LoginRequest;
import jun.dev.yourbooks.model.wraper.request.RegisterRequest;
import jun.dev.yourbooks.model.wraper.request.ResetPasswordRequest;
import jun.dev.yourbooks.model.wraper.request.UserEditRequest;
import jun.dev.yourbooks.model.wraper.response.ResponseJWT;
import jun.dev.yourbooks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request){
        userService.register(request);
        return ResponseEntity.ok("Please activate your account, activation line has been sent.");
    }
    @PostMapping("/activate/{link}")
    ResponseEntity<?> activateAccount(@PathVariable(name = "link") String link){
        userService.activate(link);
        return ResponseEntity.ok("You successfully activated account!");
    }
    @PostMapping("/login")
    ResponseEntity<ResponseJWT> login(@Valid @RequestBody LoginRequest loginRequest){
        return userService.login(loginRequest);
    }
    @PostMapping("/link/reset/password")
    ResponseEntity<?> sendLink(@RequestParam String email){
        userService.sendLink(email);
        return ResponseEntity.ok("The link has been sent in your email to reset password ");
    }
    @PostMapping("/reset/password/{link}")
    ResponseEntity<?> resetPassword(@PathVariable(name = "link") String link,
                                    @Valid @RequestBody ResetPasswordRequest request){
        userService.resetPassword(link,request);
        return ResponseEntity.ok("You successfully reset password");
    }
    @PostMapping("/edit")
    ResponseEntity<UserDto> editUser(@Valid @RequestBody UserEditRequest editRequest,
                                     @AuthenticationPrincipal User user){
        return ResponseEntity.ok(userService.edit(editRequest,user));
    }
}
