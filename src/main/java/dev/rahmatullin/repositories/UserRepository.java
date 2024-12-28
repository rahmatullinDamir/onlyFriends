package dev.rahmatullin.repositories;

import dev.rahmatullin.models.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User>{
    Optional<User> findByEmail(String email) throws SQLException;
}
