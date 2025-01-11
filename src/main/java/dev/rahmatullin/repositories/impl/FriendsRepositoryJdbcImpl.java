package dev.rahmatullin.repositories.impl;

import dev.rahmatullin.models.Friends;
import dev.rahmatullin.repositories.FriendsRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FriendsRepositoryJdbcImpl implements FriendsRepository {

    private static final String SQL_SELECT_ALL_REQUEST = "SELECT * FROM friends WHERE friend_id = ? and status = 'PENDING'";
    private static final String SQL_SELECT_ALL = "SELECT * FROM friends";
    private static final String SQL_SELECT_ALL_FRIENDS = "SELECT * FROM friends WHERE (user_id = ? OR friend_id = ?) AND status = 'ACCEPTED'";
    private static final String SQL_SELECT_ALL_FRIENDS_WITH_LIMIT = "SELECT * FROM friends WHERE (user_id = ? OR friend_id = ?) AND status = 'ACCEPTED' LIMIT ?";
    private static final String SQL_SELECT_FROM_FRIENDSHIP_BY_ID = "SELECT * FROM friends WHERE id = ?";
    private static final String SQL_INSERT_NEW_FRIENDSHIP = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?)";
    private static final String SQL_UPDATE_FRIENDSHIP = "UPDATE friends SET user_id = ?, friend_id = ?, status = ? WHERE id = ?";
    private static final String SQL_DELETE_FRIENDSHIP = "DELETE FROM friends WHERE id = ?";
    private static final String SQL_SELECT_ALL_FRIENDSHIPS_BY_USER = "SELECT * FROM friends WHERE user_id = ? AND friend_id = ? OR friend_id = ? AND user_id = ?";

    private DataSource dataSource;

    public FriendsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Friends> getFriends(Long userId) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_FRIENDS);
        statement.setLong(1, userId);
        statement.setLong(2, userId);
        ResultSet resultSet = statement.executeQuery();
        List<Friends> friends = new ArrayList<>();
        while (resultSet.next()) {
            friends.add(Friends.builder()
                            .id( resultSet.getLong("id"))
                            .userId(resultSet.getLong("user_id"))
                            .friendId(resultSet.getLong("friend_id"))
                            .status(resultSet.getString("status"))
                            .build()

            );
        }
        return friends;
    }

    @Override
    public List<Friends> getFriendsLimit(Long userId, int limit) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_FRIENDS_WITH_LIMIT);
        statement.setLong(1, userId);
        statement.setLong(2, userId);
        statement.setInt(3, limit);
        ResultSet resultSet = statement.executeQuery();
        List<Friends> friends = new ArrayList<>();
        while (resultSet.next()) {
            friends.add(Friends.builder()
                            .id(resultSet.getLong("id"))
                            .userId(resultSet.getLong("user_id"))
                            .friendId(resultSet.getLong("friend_id"))
                            .status(resultSet.getString("status"))
                    .build());
        }
        return friends;
    }

    @Override
    public List<Friends> getPendingRequests(Long userId) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_REQUEST);
        statement.setLong(1, userId);
        ResultSet resultSet = statement.executeQuery();
        List<Friends> friends = new ArrayList<>();
        while (resultSet.next()) {
            friends.add(Friends.builder()
                    .id(resultSet.getLong("id"))
                    .userId(resultSet.getLong("user_id"))
                    .friendId(resultSet.getLong("friend_id"))
                    .status(resultSet.getString("status"))
                    .build());
        }
        return friends;
    }

    @Override
    public Optional<Friends> getFriendshipByUser(Long user_id, Long friend_id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_FRIENDSHIPS_BY_USER);
        statement.setLong(1, user_id);
        statement.setLong(2, friend_id);
        statement.setLong(3, user_id);
        statement.setLong(4, friend_id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return Optional.of(Friends.builder().
                    id(resultSet.getLong("id")).
                    userId(resultSet.getLong("user_id")).
                    friendId(resultSet.getLong("friend_id")).
                    status(resultSet.getString("status")).
                    build());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Friends> findById(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FROM_FRIENDSHIP_BY_ID);
        statement.setLong(1, id);


        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return Optional.of(Friends.builder()
                    .id(resultSet.getLong("id"))
                    .userId(resultSet.getLong("user_id"))
                    .friendId(resultSet.getLong("friend_id"))
                    .status(resultSet.getString("status"))
                    .build());
        }
        return Optional.empty();
    }


    public List<Friends> findAll() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

        List<Friends> result = new ArrayList<>();

        while (resultSet.next()) {
            Friends friendship = Friends.builder()
                    .id(resultSet.getLong("id"))
                    .userId(resultSet.getLong("user_id"))
                    .friendId(resultSet.getLong("friend_id"))
                    .status(resultSet.getString("status"))
                    .build();
            result.add(friendship);
        }
        return result;
    }

    @Override
    public void save(Friends entity) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NEW_FRIENDSHIP);
        statement.setLong(1, entity.getUserId());
        statement.setLong(2, entity.getFriendId());
        statement.executeUpdate();
    }

    @Override
    public void save(List<Friends> entity, int countOfEntities) throws SQLException {
        for (Friends friend: entity) {
            save(friend);
        }
    }

    @Override
    public void update(Friends entity) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_FRIENDSHIP);
        statement.setLong(1, entity.getUserId());
        statement.setLong(2, entity.getFriendId());
        statement.setString(3, entity.getStatus());
        statement.setLong(4, entity.getId());

        statement.executeUpdate();

    }


    @Override
    public void remove(Friends entity) throws SQLException {
        removeById(entity.getId());
    }


    @Override
    public void removeById(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_FRIENDSHIP);
        statement.setLong(1, id);
        statement.executeUpdate();

    }
}
