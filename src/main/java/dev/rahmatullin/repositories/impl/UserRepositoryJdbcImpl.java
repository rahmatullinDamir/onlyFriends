package dev.rahmatullin.repositories.impl;

import dev.rahmatullin.models.User;
import dev.rahmatullin.repositories.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJdbcImpl implements UserRepository {
    private Connection connection;
    private static final String SQL_SELECT_ALL_FROM_USER_PROFILE = "SELECT * FROM user_profile";
    private static final String SQL_INSERT_TO_USER_PROFILE = "INSERT INTO user_profile (name, surname, age," +
            " password, email) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_USER_PROFILE = "UPDATE user_profile SET name = ?, surname = ?," +
            " age = ?, avatar_image_id = ?, password = ? WHERE id = ?";
    private static final String SQL_DELETE_USER_PROFILE = "DELETE FROM user_profile WHERE id = ?";

    public UserRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Optional<User> findById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_FROM_USER_PROFILE + " WHERE id = ?");
        statement.setLong(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return Optional.of(new User(resultSet.getLong(1), resultSet.getInt("age"),
                    resultSet.getString("name"), resultSet.getString("surname"),
                    resultSet.getString("password"),resultSet.getString("role"),
                    resultSet.getLong("avatar_image_id"), resultSet.getString("email")
            ));
        }

        return Optional.empty();
    }

    @Override
    public void save(User entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_INSERT_TO_USER_PROFILE);

        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
        statement.setInt(3, entity.getAge());
        statement.setString(4, entity.getPassword());
        statement.setString(5, entity.getEmail());

        statement.executeUpdate();
    }

    @Override
    public void save(List<User> entity, int countOfEntities) throws SQLException {
        for (int i = 0; i < countOfEntities; i++) {
            save(entity.get(i));
        }
    }

    @Override
    public void update(User entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_PROFILE);

        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
        statement.setInt(3, entity.getAge());
        statement.setLong(4, entity.getAvatarImageId());
        statement.setString(5, entity.getPassword());;
        statement.setLong(6, entity.getId());
    }

    @Override
    public void remove(User entity) throws SQLException {
        removeById(entity.getId());
    }

    @Override
    public void removeById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_PROFILE);
        statement.setLong(1, id);
        statement.executeUpdate();
    }

    @Override
    public Optional<User> findByEmail(String email) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_FROM_USER_PROFILE + " WHERE email = ?");
        statement.setString(1, email);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return Optional.of(new User(resultSet.getLong(1), resultSet.getInt("age"),
                    resultSet.getString("name"), resultSet.getString("surname"),
                    resultSet.getString("password"),resultSet.getString("role"),
                    resultSet.getLong("avatar_image_id"), resultSet.getString("email")
            ));
        }

        return Optional.empty();
    }
}
