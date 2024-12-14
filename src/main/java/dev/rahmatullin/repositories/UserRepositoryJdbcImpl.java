package dev.rahmatullin.repositories;

import dev.rahmatullin.models.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJdbcImpl implements UserRepository {
    private Connection connection;
    UserRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Optional<User> findById(Long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public void save(User entity) throws SQLException {

    }

    @Override
    public void save(List<User> entity, int countOfEntities) throws SQLException {

    }

    @Override
    public void update(User entity) throws SQLException {

    }

    @Override
    public void remove(User entity) throws SQLException {

    }

    @Override
    public void removeById(Long id) throws SQLException {

    }
}
