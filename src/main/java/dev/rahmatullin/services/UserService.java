package dev.rahmatullin.services;

import dev.rahmatullin.dto.UserDto;
import dev.rahmatullin.models.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    void deleteUserById(Long userId) throws SQLException;
    List<UserDto> getAllUsers() throws SQLException;
    UserDto getUserById(Long userId) throws SQLException;
    UserDto getUserByUsername(String username) throws SQLException;
    void updateUserInDbWithoutImage(User user) throws SQLException;

    boolean addFriend(Long userId, Long friendId, String status) throws SQLException;
    void updateUserInDb(User user);

    boolean isEmailTaken(String email) throws SQLException;
    boolean isUsernameTaken(String username) throws SQLException;
}
