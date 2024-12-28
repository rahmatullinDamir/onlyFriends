package dev.rahmatullin.services.impl;

import dev.rahmatullin.dto.SignInForm;
import dev.rahmatullin.dto.UserDto;
import dev.rahmatullin.models.User;
import dev.rahmatullin.repositories.UserRepository;
import dev.rahmatullin.services.SignInService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;
import java.util.Optional;

public class SignInServiceImpl implements SignInService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public SignInServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    @Override
    public UserDto signIn(SignInForm form) throws SQLException {
         Optional<User> userOptional = userRepository.findByEmail(form.getEmail());
         if (userOptional.isPresent()) {
             User user = userOptional.get();
             if (passwordEncoder.matches(form.getPassword(), user.getPassword())) {
                return UserDto.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .role(user.getRole())
                        .build();
             }
         }

         return null;
        // функция проверки пароля
//        Boolean pass = passwordEncoder.matches(rawPss, encodePass);
        // rawPass - пароль полученный с фронта
        // encodePass - ароль из БД

    }
}
