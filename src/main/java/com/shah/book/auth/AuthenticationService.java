package com.shah.book.auth;

import com.shah.book.email.EmailService;
import com.shah.book.email.EmailTemplateName;
import com.shah.book.role.RoleRepository;
import com.shah.book.user.Token;
import com.shah.book.user.TokenRepository;
import com.shah.book.user.User;
import com.shah.book.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private RoleRepository repository;
    private TokenRepository tokenRepository;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;

    @Autowired
    public AuthenticationService(UserRepository userRepository, RoleRepository repository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.repository = repository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public AuthenticationService() {
    }

    public AuthenticationService(UserRepository userRepository, RoleRepository repository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, EmailService emailService, String activationUrl) {
        this.userRepository = userRepository;
        this.repository = repository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.activationUrl = activationUrl;
    }

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    public AuthenticationService(RoleRepository repository, UserRepository userRepository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, EmailService emailService, String activationUrl) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.activationUrl = activationUrl;
    }

    public void register(RegistrationRequest request) throws MessagingException {
        var userRole = repository.findByName("USER")
                .orElseThrow(()-> new IllegalStateException("Role user was not initialized"));
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
//        TO DO Send Email
        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account Activation"
        );
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedCode = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedCode)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedCode;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++){
            int randomIndex = random.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
}
