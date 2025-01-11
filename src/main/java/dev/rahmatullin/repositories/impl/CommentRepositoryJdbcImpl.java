package dev.rahmatullin.repositories.impl;

import dev.rahmatullin.models.Comment;
import dev.rahmatullin.repositories.CommentRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentRepositoryJdbcImpl implements CommentRepository {
    private DataSource dataSource;
    private Connection connection;
    private static final String SQL_SELECT_ALL_FROM_COMMENT = "SELECT * FROM comment";
    private static final String SQL_INSERT_TO_COMMENT = "INSERT INTO comment (text, user_id, post_id, author) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_COMMENT = "UPDATE comment SET text = ?, user_id = ?, post_id = ?, author = ? WHERE id = ?";
    private static final String SQL_DELETE_COMMENT = "DELETE FROM comment WHERE id = ?";
    private static final String SQL_SELECT_AlL_COMMENTS_WHERE_POST_ID = "SELECT * FROM comment WHERE post_id = ?";

    public CommentRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Comment> findById(Long id) throws SQLException {
        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_FROM_COMMENT + " WHERE id = ?");
        statement.setLong(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return Optional.of(new Comment(resultSet.getLong("id"),
                    resultSet.getString("text"),
                    resultSet.getLong("user_id"),
                    resultSet.getDate("date"),
                    resultSet.getLong("post_id"),
                    resultSet.getString("author")
            ));
        }

        return Optional.empty();
    }

    @Override
    public void save(Comment entity) throws SQLException {
        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_INSERT_TO_COMMENT);

        statement.setString(1, entity.getText());
        statement.setLong(2, entity.getUserId());
        statement.setLong(3, entity.getPostId());
        statement.setString(4, entity.getAuthor());

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
        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_COMMENT);

        statement.setString(1, entity.getText());
        statement.setLong(2, entity.getUserId());
        statement.setLong(3, entity.getPostId());
        statement.setString(4, entity.getAuthor());

        statement.executeUpdate();
    }

    @Override
    public void remove(Comment entity) throws SQLException {
        removeById(entity.getId());
    }
    public List<Comment> getCommentsByPostId(Long postId) throws SQLException {
        List<Comment> comments = new ArrayList<>();

        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_AlL_COMMENTS_WHERE_POST_ID);
        statement.setLong(1, postId);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            comments.add(
                    new Comment(resultSet.getLong("id"),
                            resultSet.getString("text"),
                            resultSet.getLong("user_id"),
                            resultSet.getDate("date"),
                            resultSet.getLong("post_id"),
                            resultSet.getString("author")
                    )
            );
        }
        return comments;

    }
    @Override
    public void removeById(Long id) throws SQLException {
        connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_COMMENT);
        statement.setLong(1, id);
        statement.executeUpdate();
    }
}