package dev.rahmatullin.repositories.impl;

import dev.rahmatullin.models.Likes;
import dev.rahmatullin.repositories.LikesRepository;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class LikesRepositoryJdbcImpl implements LikesRepository {
    private Connection connection;
    private DataSource dataSource;

    private static final String SQL_SELECT_ALL_FROM_LIKES = "SELECT * FROM likes";
    private static final String SQL_INSERT_TO_LIKES = "INSERT INTO likes (date) VALUES (?)";
    private static final String SQL_UPDATE_LIKES = "UPDATE likes SET date = ? WHERE id = ?";
    private static final String SQL_DELETE_LIKES = "DELETE FROM likes WHERE id = ?";

    private static final String SQL_SELECT_LIKE_FROM_POST_AND_USER = "SELECT like_id FROM post_like WHERE post_id = ? AND user_id = ?";
    private static final String SQL_INSERT_LIKE = "INSERT INTO likes DEFAULT VALUES RETURNING id";
    private static final String SQL_INSEERT_POST_LIKE = "INSERT INTO post_like (post_id, user_id, like_id) VALUES (?, ?, ?)";
    private static final String SQL_DELETE_POST_LIKE = "DELETE FROM post_like WHERE post_id = ? AND user_id = ? AND like_id = ?";
    private static final String SQL_DELETE_LIKE = "DELETE FROM likes WHERE id = ?";

    private static final String SQL_SELECT_COUNT_LIKES = "SELECT COUNT(like_id) AS like_count FROM post_like WHERE post_id = ?";
    private String SQL_IS_POST_LIKED_BY_USER = "SELECT * FROM post_like WHERE post_id = ? AND user_id = ?";
    public LikesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Likes> findById(Long id) throws SQLException {
        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_FROM_LIKES + " WHERE id = ?");
        statement.setLong(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return Optional.of(new Likes(resultSet.getLong("id"), resultSet.getDate("date")));
        }

        return Optional.empty();
    }
    @Override
    public void save(Likes entity) throws SQLException {
        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_INSERT_TO_LIKES);

        statement.setDate(1, Date.valueOf(String.valueOf(entity.getDate())));
        statement.executeUpdate();
    }

    @Override
    public void save(List<Likes> entities, int countOfEntities) throws SQLException {
        for (int i = 0; i < countOfEntities; i++) {
            save(entities.get(i));
        }
    }

    @Override
    public void update(Likes entity) throws SQLException {
        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_LIKES);

        statement.setDate(2, Date.valueOf(String.valueOf(entity.getDate())));
        statement.setLong(1, entity.getId());
        statement.executeUpdate();
    }

    @Override
    public void remove(Likes entity) throws SQLException {
        removeById(entity.getId());
    }

    @Override
    public void removeById(Long id) throws SQLException {
        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_LIKES);
        statement.setLong(1, id);
        statement.executeUpdate();
    }

    @Override
    public Long getLikeIdByPostAndUser(Long postId, Long userId) throws SQLException {
        connection = dataSource.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_LIKE_FROM_POST_AND_USER)) {
            preparedStatement.setLong(1, postId);
            preparedStatement.setLong(2, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("like_id");
                }
            }
        }
        return null;
    }

    @Override
    public void addLike(Long postId, Long userId) throws SQLException {
        connection = dataSource.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_LIKE)) {
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    int likeId = rs.getInt("id");


                    try (PreparedStatement prepareStatement2 = connection.prepareStatement(SQL_INSEERT_POST_LIKE)) {
                        prepareStatement2.setLong(1, postId);
                        prepareStatement2.setLong(2, userId);
                        prepareStatement2.setLong(3, likeId);
                        prepareStatement2.executeUpdate();
                    }
                }
            }
        }
    }

    @Override
    public void removeLike(Long likeId, Long postId, Long userId) throws SQLException {
        connection = dataSource.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_POST_LIKE)) {
            preparedStatement.setLong(1, postId);
            preparedStatement.setLong(2, userId);
            preparedStatement.setLong(3, likeId);
            preparedStatement.executeUpdate();
        }


        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_LIKE)) {
            preparedStatement.setLong(1, likeId);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Long getLikeCount(Long postId) throws SQLException {
        connection = dataSource.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COUNT_LIKES)) {
            preparedStatement.setLong(1, postId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("like_count");
                }
            }
        }
        return 0L;
    }

    @Override
    public boolean isPostLikedByUser(Long postId, Long userId) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_IS_POST_LIKED_BY_USER)) {
            statement.setLong(1, postId);
            statement.setLong(2, userId);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}