package dev.rahmatullin.services.impl;

import dev.rahmatullin.dto.SignUpForm;
import dev.rahmatullin.models.User;
import dev.rahmatullin.repositories.UserRepository;
import dev.rahmatullin.services.SignUpService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;

public class SignUpServiceImpl implements SignUpService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public SignUpServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();

    }

    @Override
    public void signUp(SignUpForm form) throws SQLException {
        User user = User.builder()
                .name(form.getName())
                .surname(form.getSurname())
                .age(form.getAge())
                .password(passwordEncoder.encode(form.getPassword()))
                .email(form.getEmail())
                .username(form.getUsername())
                .build();
        userRepository.save(user);
    }
}
