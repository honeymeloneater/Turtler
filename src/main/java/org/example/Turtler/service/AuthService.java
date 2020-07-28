package org.example.Turtler.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Turtler.dto.AuthenticationResponse;
import org.example.Turtler.dto.LoginRequest;
import org.example.Turtler.dto.RegisterRequest;
import org.example.Turtler.exception.TurtlerException;
import org.example.Turtler.model.*;
import org.example.Turtler.repository.UserRepository;
import org.example.Turtler.repository.VerificationTokenRepository;
import org.example.Turtler.security.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
import java.util.UUID;

import static java.time.Instant.now;
import static org.example.Turtler.util.Constants.ACTIVATION_EMAIL;

@Service
@AllArgsConstructor
@Slf4j

public class AuthService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final VerificationTokenRepository verificationTokenRepository;
        private final MailContentBuilder mailContentBuilder;
        private final MailService mailService;
        private final AuthenticationManager authenticationManager;
        private final JwtProvider jwtProvider;

        @Transactional
        public void signup(RegisterRequest registerRequest) {
            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(encodePassword(registerRequest.getPassword()));
            user.setCreated(now());
            user.setEnabled(false);

            userRepository.save(user);

            String token = generateVerificationToken(user);
            String message = mailContentBuilder.build("Thank you for signing up to Turtler, please click on the below url to activate your account : "
                    + ACTIVATION_EMAIL + "/" + token);
            mailService.sendMail(new NotificationEmail("Please Activate your account", user.getEmail(), message));
        }

        private String encodePassword(String password){
            return passwordEncoder.encode(password);
        }
    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
        verificationTokenOptional.orElseThrow(() -> new TurtlerException("Invalid Token"));
        fetchUserAndEnable(verificationTokenOptional.get());
    }
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }
    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new TurtlerException("User Not Found with id - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }
        private String generateVerificationToken(User user){
            String token = UUID.randomUUID().toString();
            VerificationToken verificationToken = new VerificationToken();
            verificationToken.setToken(token);
            verificationToken.setUser(user);
            verificationTokenRepository.save(verificationToken);
            return token;
        }
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
    }
}
