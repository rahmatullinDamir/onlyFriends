package dev.rahmatullin.repositories;

import dev.rahmatullin.dto.UserDto;
import dev.rahmatullin.models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User>{
    List<UserDto> getAllUsers() throws SQLException;
    Optional<User> findByEmail(String email) throws SQLException;
    Optional<User> findByUsername(String username) throws SQLException;
    boolean addFriend(Long userId, Long friendId, String status) throws SQLException;
    boolean isEmailTaken(String email) throws SQLException;
    boolean isUsernameTaken(String username) throws SQLException;
    void updateWithoutImage(User entity) throws SQLException;
}
