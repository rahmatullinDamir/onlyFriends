package dev.rahmatullin.services;

import dev.rahmatullin.dto.UserDto;

import java.sql.SQLException;

public interface UserService {
    UserDto getUserById(Long userId) throws SQLException;
    UserDto getUserByUsername(String username) throws SQLException;
}
