package dev.rahmatullin.repositories.impl;

import dev.rahmatullin.models.Comment;
import dev.rahmatullin.repositories.CommentRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class CommentRepositoryJdbcImpl implements CommentRepository {
    private Connection connection;

    private static final String SQL_SELECT_ALL_FROM_COMMENT = "SELECT * FROM comment";
    private static final String SQL_INSERT_TO_COMMENT = "INSERT INTO comment (text, user_id, date) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_COMMENT = "UPDATE comment SET text = ?, user_id = ?, date = ? WHERE id = ?";
    private static final String SQL_DELETE_COMMENT = "DELETE FROM comment WHERE id = ?";

    public CommentRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Comment> findById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_FROM_COMMENT + " WHERE id = ?");
        statement.setLong(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return Optional.of(new Comment(resultSet.getLong("id"),
                    resultSet.getString("text"),
                    resultSet.getLong("user_id"),
                    resultSet.getDate("date")));
        }

        return Optional.empty();
    }

    @Override
    public void save(Comment entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_INSERT_TO_COMMENT);

        statement.setString(1, entity.getText());
        statement.setLong(2, entity.getUserId());
        statement.setDate(3, Date.valueOf(String.valueOf(entity.getDate())));

        statement.executeUpdate();
    }

    @Override
    public void save(List<Comment> entities, int countOfEntities) throws SQLException {
        for (int i = 0; i < countOfEntities; i++) {
            save(entities.get(i));
        }
    }

    @Override
    public void update(Comment entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_COMMENT);

        statement.setString(1, entity.getText());
        statement.setLong(2, entity.getUserId());
        statement.setDate(3, Date.valueOf(String.valueOf(entity.getDate())));
        statement.setLong(4, entity.getId());

        statement.executeUpdate();
    }

    @Override
    public void remove(Comment entity) throws SQLException {
        removeById(entity.getId());
    }

    @Override
    public void removeById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_COMMENT);
        statement.setLong(1, id);
        statement.executeUpdate();
    }
}