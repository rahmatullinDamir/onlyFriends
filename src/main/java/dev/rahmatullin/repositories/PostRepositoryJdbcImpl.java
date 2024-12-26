package dev.rahmatullin.repositories;

import dev.rahmatullin.models.Post;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class PostRepositoryJdbcImpl implements PostRepository {
    private Connection connection;

    private static final String SQL_SELECT_ALL_FROM_POST = "SELECT * FROM post";
    private static final String SQL_INSERT_TO_POST = "INSERT INTO post (text, user_id, date) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_POST = "UPDATE post SET text = ?, user_id = ?, date = ? WHERE id = ?";
    private static final String SQL_DELETE_POST = "DELETE FROM post WHERE id = ?";

    public PostRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Post> findById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_FROM_POST + " WHERE id = ?");
        statement.setLong(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return Optional.of(new Post(resultSet.getLong("id"),
                    resultSet.getString("text"),
                    resultSet.getLong("user_id"),
                    resultSet.getDate("date")));
        }

        return Optional.empty();
    }

    @Override
    public void save(Post entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_INSERT_TO_POST);

        statement.setString(1, entity.getText());
        statement.setLong(2, entity.getUserId());
        statement.setDate(3, Date.valueOf(String.valueOf(entity.getDate())));

        statement.executeUpdate();
    }

    @Override
    public void save(List<Post> entities, int countOfEntities) throws SQLException {
        for (int i = 0; i < countOfEntities; i++) {
            save(entities.get(i));
        }
    }

    @Override
    public void update(Post entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_POST);

        statement.setString(1, entity.getText());
        statement.setLong(2, entity.getUserId());
        statement.setDate(3, Date.valueOf(entity.getDate().toString()));
        statement.setLong(4, entity.getId());

        statement.executeUpdate();
    }

    @Override
    public void remove(Post entity) throws SQLException {
        removeById(entity.getId());
    }

    @Override
    public void removeById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_POST);
        statement.setLong(1, id);
        statement.executeUpdate();
    }
}