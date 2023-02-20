package jun.dev.yourbooks.service.impls;

import jun.dev.yourbooks.exception.EmailExistException;
import jun.dev.yourbooks.exception.ExpiredException;
import jun.dev.yourbooks.exception.FileException;
import jun.dev.yourbooks.exception.NotFoundException;
import jun.dev.yourbooks.mapper.ActivationTokenMapper;
import jun.dev.yourbooks.mapper.ResetPasswordMapper;
import jun.dev.yourbooks.mapper.UserMapper;
import jun.dev.yourbooks.model.dto.UserDto;
import jun.dev.yourbooks.model.entity.ActivationToken;
import jun.dev.yourbooks.model.entity.ResetPassword;
import jun.dev.yourbooks.model.entity.User;
import jun.dev.yourbooks.model.dto.request.LoginRequest;
import jun.dev.yourbooks.model.dto.request.RegisterRequest;
import jun.dev.yourbooks.model.dto.request.ResetPasswordRequest;
import jun.dev.yourbooks.model.dto.request.UserEditRequest;
import jun.dev.yourbooks.model.dto.response.ResponseJWT;
import jun.dev.yourbooks.repository.ActivationTokenRepo;
import jun.dev.yourbooks.repository.ResetPasswordRepo;
import jun.dev.yourbooks.repository.UserRepo;
import jun.dev.yourbooks.service.MailService;
import jun.dev.yourbooks.service.UserService;
import jun.dev.yourbooks.util.CloudStorage;
import jun.dev.yourbooks.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final ActivationTokenMapper activationTokenMapper;
    private final ActivationTokenRepo activationTokenRepo;
    private final MailService mailService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ResetPasswordMapper resetPasswordMapper;
    private final ResetPasswordRepo resetPasswordRepo;
    private final CloudStorage cloudStorage;

    @Override
    public void register(RegisterRequest request) {
        if (userRepo.existsUserByEmail(request.getEmail()))
            throw new EmailExistException("Email already registered");

        request.setPassword(encoder.encode(request.getPassword()));
        ActivationToken activationToken = activationTokenMapper.toEntity(request);
        ActivationToken link = activationTokenRepo.save(activationToken);
        mailService.sendMessage(link.getEmail(), "Activate account", "Here is the link below, " +
                "please click to activate account \n" +
                "localhost:8080/user/activate/" + link.getLink());
    }

    @Override
    public void activate(String link) {
        ActivationToken activationToken = validateActivationLink(link);
        User user = userMapper.toUserToken(activationToken);
        userRepo.save(user);
    }

    @Override
    public ResponseEntity<ResponseJWT> login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(authentication);
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(userMapper.toJwt(user, jwt));
    }

    @Override
    public void sendLink(String email) {
        User user = findUserByEmail(email);
        ResetPassword link = resetPasswordMapper.toResetPassword(user);
        ResetPassword savedLink = resetPasswordRepo.save(link);
        mailService.sendMessage(user.getEmail(), "Reset password", "Here is the link bellow, click to reset password" +
                "\n localhost:8080/user/reset/password/" + savedLink.getLink());
    }

    @Override
    public void resetPassword(String link, ResetPasswordRequest request) {
        ResetPassword resetPassword = validateResetPasswordLink(link);
        if (!request.getNewPassword().equals(request.getRepeatPassword()))
            throw new RuntimeException("Password does not match each other!");

        User user = findUserByEmail(resetPassword.getUser().getEmail());
        user.setPassword(encoder.encode(request.getRepeatPassword()));
        userRepo.save(user);
    }

    @Override
    public UserDto edit(UserEditRequest editRequest, User user) {
        String imageUrl = checkAvatar(editRequest.getImageFile());
        checkExistingImageAndDelete(user.getAvatarUrl());
        User editedUser = userMapper.toUserFromEdit(editRequest, imageUrl, user);
        userRepo.save(editedUser);
        return userMapper.toDto(editedUser);
    }

    @Override
    public UserDto findUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("No such a user found with " + id));
        return userMapper.toDto(user);
    }

    private String checkAvatar(MultipartFile file) {
        if (file == null)
            return "anon.jpg";
        if (!cloudStorage.isImageFile(file))
            throw new FileException("Avatar is not image file!");
        return cloudStorage.uploadFile(file);
    }

    private ActivationToken validateActivationLink(String link) {
        ActivationToken activationToken = activationTokenRepo.findActivationTokenByLink(link).orElseThrow(() ->
                new NotFoundException("The link doesn't exist"));
        if (activationToken.getCreatedAt().plusMinutes(30).isBefore(LocalDateTime.now())) {
            throw new ExpiredException("The link has been expired");
        }
        activationTokenRepo.delete(activationToken);
        return activationToken;
    }

    private ResetPassword validateResetPasswordLink(String link) {
        ResetPassword resetPassword = resetPasswordRepo.findResetPasswordByLink(link).orElseThrow(() ->
                new NotFoundException("No such a link exists!"));
        if (resetPassword.getCreatedAt().plusMinutes(30).isBefore(LocalDateTime.now()))
            throw new ExpiredException("This link has been expired");
        return resetPassword;
    }

    private User findUserByEmail(String email) {
        return userRepo.findUserByEmail(email)
                .orElseThrow(() -> new NotFoundException("No such a user found with email " + email));
    }

    private void checkExistingImageAndDelete(String imageUrl) {
        if (!imageUrl.equals("anon.jpg")) {
            String imageName = imageUrl.substring(48);
            cloudStorage.deleteImage(imageName);
        }
    }

}
