package dev.rahmatullin.repositories;

import dev.rahmatullin.models.Post;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PostRepositoryJdbcImpl implements PostRepository{
    private Connection connection;
    PostRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Optional<Post> findById(Long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public void save(Post entity) throws SQLException {

    }

    @Override
    public void save(List<Post> entity, int countOfEntities) throws SQLException {

    }

    @Override
    public void update(Post entity) throws SQLException {

    }

    @Override
    public void remove(Post entity) throws SQLException {

    }

    @Override
    public void removeById(Long id) throws SQLException {

    }
}
