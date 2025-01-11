package dev.rahmatullin.services.impl;

import dev.rahmatullin.dto.UserDto;
import dev.rahmatullin.models.User;
import dev.rahmatullin.repositories.UserRepository;
import dev.rahmatullin.services.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void deleteUserById(Long userId) throws SQLException {
        userRepository.removeById(userId);
    }

    @Override
    public List<UserDto> getAllUsers() throws SQLException {
        return userRepository.getAllUsers();
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
                    .id(user.getId())
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
    public boolean addFriend(Long userId, Long friendId, String status) throws SQLException {
       return userRepository.addFriend(userId, friendId, status);
    }

    @Override
    public void updateUserInDb(User user) {
        try {
            userRepository.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUserInDbWithoutImage(User user) {
        try {
            userRepository.updateWithoutImage(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public boolean isEmailTaken(String email) throws SQLException {
        return userRepository.isEmailTaken(email);
    }

    @Override
    public boolean isUsernameTaken(String username) throws SQLException {
        return userRepository.isUsernameTaken(username);
    }
}
