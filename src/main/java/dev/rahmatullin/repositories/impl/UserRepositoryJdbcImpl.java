package dev.rahmatullin.repositories.impl;

import dev.rahmatullin.dto.UserDto;
import dev.rahmatullin.models.User;
import dev.rahmatullin.repositories.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJdbcImpl implements UserRepository {
    private Connection connection;

    private DataSource dataSource;
    private static final String SQL_SELECT_ALL_FROM_USER_PROFILE = "SELECT * FROM user_profile";

    private static final String SQL_INSERT_TO_USER_PROFILE = "INSERT INTO user_profile (name, surname, age," +
            " password, email, username) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_USER_PROFILE = "UPDATE user_profile SET name = ?, surname = ?," +
            " age = ?, avatar_image_id = ? WHERE id = ?";
    private static final String SQL_UPDATE_USER_PROFILE_WITHOUT_IMAGE = "UPDATE user_profile SET name = ?, surname = ?," +
            " age = ? WHERE id = ?";
    private static final String SQL_DELETE_USER_PROFILE = "DELETE FROM user_profile WHERE id = ?";
    private static final String SQL_ADD_FRIEND = "INSERT INTO friends (user_id, friend_id, status) VALUES (?, ?, ?)";

    private static final String SQL_SELECT_ALL_FROM_USER_PROFILE_WHERE_USERNAME = "SELECT * FROM user_profile WHERE username = ?";
    private static final String SQL_SELECT_ALL_FROM_USER_PROFILE_WHERE_EMAIl = "SELECT * FROM user_profile WHERE email = ?";

    public UserRepositoryJdbcImpl(DataSource dataSource)  {
        this.dataSource = dataSource;
    }
    @Override
    public Optional<User> findById(Long id) throws SQLException {
        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_FROM_USER_PROFILE + " WHERE id = ?");
        statement.setLong(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return Optional.of(new User(resultSet.getLong(1), resultSet.getInt("age"),
                    resultSet.getString("name"), resultSet.getString("surname"),
                    resultSet.getString("password"),resultSet.getString("role"),
                    resultSet.getLong("avatar_image_id"), resultSet.getString("email"),
                    resultSet.getString("username")
            ));
        }

        return Optional.empty();
    }

    @Override
    public void save(User entity) throws SQLException {
        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_INSERT_TO_USER_PROFILE);

        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
        statement.setInt(3, entity.getAge());
        statement.setString(4, entity.getPassword());
        statement.setString(5, entity.getEmail());
        statement.setString(6, entity.getUsername());

        statement.executeUpdate();
    }

    @Override
    public void save(List<User> entity, int countOfEntities) throws SQLException {
        for (int i = 0; i < countOfEntities; i++) {
            save(entity.get(i));
        }
    }

    @Override
    public void updateWithoutImage(User entity) throws SQLException {
        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_PROFILE_WITHOUT_IMAGE);

        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
        statement.setInt(3, entity.getAge());
        statement.setLong(4, entity.getId());
        statement.executeUpdate();
    }
    @Override
    public void update(User entity) throws SQLException {
        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_PROFILE);

        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
        statement.setInt(3, entity.getAge());
        statement.setLong(4, entity.getAvatarImageId());
        statement.setLong(5, entity.getId());
        statement.executeUpdate();
    }

    @Override
    public void remove(User entity) throws SQLException {
        removeById(entity.getId());
    }

    @Override
    public void removeById(Long id) throws SQLException {
        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_PROFILE);
        statement.setLong(1, id);
        statement.executeUpdate();
    }

    @Override
    public List<UserDto> getAllUsers() throws SQLException {
        connection = dataSource.getConnection();
        List<UserDto> users = new ArrayList<>();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_FROM_USER_PROFILE);

        while(resultSet.next()) {
            users.add(UserDto.builder()
                            .id(resultSet.getLong("id"))
                            .age( resultSet.getInt("age"))
                            .name(resultSet.getString("name"))
                            .surname(resultSet.getString("surname"))
                            .username( resultSet.getString("username"))
                    .build());
        }
        return users;
    }

    @Override
    public Optional<User> findByEmail(String email) throws SQLException {
        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_FROM_USER_PROFILE + " WHERE email = ?");
        statement.setString(1, email);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return Optional.of(new User(resultSet.getLong(1), resultSet.getInt("age"),
                    resultSet.getString("name"), resultSet.getString("surname"),
                    resultSet.getString("password"),resultSet.getString("role"),
                    resultSet.getLong("avatar_image_id"), resultSet.getString("email"),
                    resultSet.getString("username")
            ));
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) throws SQLException {
        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_FROM_USER_PROFILE + " WHERE username = ?");
        statement.setString(1, username);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return Optional.of(new User(resultSet.getLong(1), resultSet.getInt("age"),
                    resultSet.getString("name"), resultSet.getString("surname"),
                    resultSet.getString("password"),resultSet.getString("role"),
                    resultSet.getLong("avatar_image_id"), resultSet.getString("email"),
                    resultSet.getString("username")
            ));
        }

        return Optional.empty();
    }

    @Override
    public boolean addFriend(Long userId, Long friendId, String status) throws SQLException {
        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_ADD_FRIEND);
        statement.setLong(1, userId);
        statement.setLong(2, friendId);
        statement.setString(3, status);



        return statement.executeUpdate() > 0;
    }

    @Override
    public boolean isEmailTaken(String email) throws SQLException {
        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_FROM_USER_PROFILE_WHERE_EMAIl);
        statement.setString(1, email);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getLong(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public boolean isUsernameTaken(String username) throws SQLException {
        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_FROM_USER_PROFILE_WHERE_USERNAME);
        statement.setString(1, username);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getLong(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }
}
