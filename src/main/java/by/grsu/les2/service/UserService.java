package by.grsu.les2.service;

import by.grsu.les2.entity.User;
import by.grsu.les2.exception.UsernameAlreadyExistsException;
import by.grsu.les2.repository.UserRepository;
import by.grsu.les2.service.dao.SignUp;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User registration(SignUp request) {
        String username = request.getEmail();
        log.debug(String.format("Registration new user [username = %s]", username));

        if (isUsernameAlreadyExists(username)) {
            String message = String.format("Username \"%s\" already exists", username);
            log.warn(message);
            throw new UsernameAlreadyExistsException(message);
        }

        User user = generateNewUserFromRequest(request);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug(String.format("Load user by email [%s]", username));

        return userRepository.getUserByEmailIgnoreCase(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("User with email \"%s\" not found", username))
                );
    }


    public boolean isUsernameAlreadyExists(@NonNull String email){
        return userRepository.existsByEmailIgnoreCase(email);
    }

    private User generateNewUserFromRequest(SignUp request){
        log.debug(String.format("Generate model for user [username = %s, password = xxx]", request.getEmail()));
        return User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(new HashSet<>(List.of("ROLE_USER")))
                .build();
    }
}
