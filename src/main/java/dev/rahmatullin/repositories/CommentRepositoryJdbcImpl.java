package dev.rahmatullin.repositories;

import dev.rahmatullin.models.Comment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CommentRepositoryJdbcImpl implements CommentRepository{
    private Connection connection;
    CommentRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Optional<Comment> findById(Long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public void save(Comment entity) throws SQLException {

    }

    @Override
    public void save(List<Comment> entity, int countOfEntities) throws SQLException {

    }

    @Override
    public void update(Comment entity) throws SQLException {

    }

    @Override
    public void remove(Comment entity) throws SQLException {

    }

    @Override
    public void removeById(Long id) throws SQLException {

    }
}
