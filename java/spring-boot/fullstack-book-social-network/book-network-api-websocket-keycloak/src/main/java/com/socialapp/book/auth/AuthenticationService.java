package com.socialapp.book.auth;

import com.socialapp.book.email.EmailService;
import com.socialapp.book.email.EmailTemplateName;
import com.socialapp.book.role.Role;
import com.socialapp.book.role.RoleRepository;
import com.socialapp.book.security.JwtService;
import com.socialapp.book.user.Token;
import com.socialapp.book.user.TokenRepository;
import com.socialapp.book.user.User;
import com.socialapp.book.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

//@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

//    public void register(RegistrationRequest registrationRequest) throws MessagingException {
////        default role will be USER, todo - better exception handling
//        Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new IllegalStateException("Role USER was not initialized"));
//        User user = User.builder()
//                .firstname(registrationRequest.getFirstname())
//                .lastname(registrationRequest.getLastname())
//                .email(registrationRequest.getEmail())
//                .password(passwordEncoder.encode(registrationRequest.getPassword()))
//                .accountLocked(false)
//                .enabled(false)
//                .roles(List.of(userRole))
//                .build();
//        userRepository.save(user);
//        sendValidationEmail(user);
//    }
//
//    private void sendValidationEmail(User user) throws MessagingException {
//        String newToken = generateAndSaveActivationToken(user);
////        send email
//        emailService.sendEmail(
//                user.getEmail(),
//                user.fullName(),
//                EmailTemplateName.ACTIVATE_ACCOUNT,
//                activationUrl,
//                newToken,
//                "Account activation"
//        );
//    }
//
//    private String generateAndSaveActivationToken(User user) {
////        generate a token
//        String generatedToken = generateActivationCode(6);
//        Token token = Token.builder()
//                .token(generatedToken)
//                .createdAt(LocalDateTime.now())
//                .expiresAt(LocalDateTime.now().plusMinutes(15))
//                .user(user)
//                .build();
//        tokenRepository.save(token);
//        return generatedToken;
//    }
//
//    private String generateActivationCode(int length) {
//        String characters = "0123456789";
//        StringBuilder codeBuilder = new StringBuilder();
////        better to use SecureRandom rather than Random class
//        SecureRandom secureRandom = new SecureRandom();
//        for (int i = 0; i < length; ++i) {
//            int randomIndex = secureRandom.nextInt(characters.length());
//            codeBuilder.append(characters.charAt(randomIndex));
//        }
//        return codeBuilder.toString();
//    }
//
//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                )
//        );
//        HashMap<String, Object> claims = new HashMap<>();
//        User user = ((User) authentication.getPrincipal());
//        claims.put("fullName", user.fullName());
//        String token = jwtService.generateToken(claims, user);
//        return AuthenticationResponse.builder().fullName(user.fullName()).token(token).build();
//    }
//
//    @Transactional
//    public void activateAccount(String token) throws MessagingException {
//        Token savedToken = tokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Invalid token"));
//        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
//            sendValidationEmail(savedToken.getUser());
//            throw new RuntimeException("Activation token has expired. A new token has been sent to the same email address");
//        }
//        User user = userRepository.findById(savedToken.getUser().getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        user.setEnabled(true);
//        userRepository.save(user);
//        savedToken.setValidatedAt(LocalDateTime.now());
//        tokenRepository.save(savedToken);
//    }
}
