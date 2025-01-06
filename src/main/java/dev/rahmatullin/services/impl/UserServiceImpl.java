package dev.rahmatullin.services.impl;

import dev.rahmatullin.dto.UserDto;
import dev.rahmatullin.models.User;
import dev.rahmatullin.repositories.UserRepository;
import dev.rahmatullin.services.UserService;

import java.sql.SQLException;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto getUserById(Long userId) throws SQLException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return UserDto.builder()
                    .name(user.getName())
                    .surname(user.getSurname())
                    .avatarImageId(user.getAvatarImageId())
                    .role(user.getRole())
                    .age(user.getAge())
                    .username(user.getUsername())
                    .build();
        }
        return null;
    }

    @Override
    public UserDto getUserByUsername(String username) throws SQLException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            return UserDto.builder()
                    .name(user.getName())
                    .surname(user.getSurname())
                    .avatarImageId(user.getAvatarImageId())
                    .role(user.getRole())
                    .age(user.getAge())
                    .username(user.getUsername())
                    .build();
        }
        return null;
    }
}
