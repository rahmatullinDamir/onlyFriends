package dev.rahmatullin.repositories;

import dev.rahmatullin.models.Likes;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class LikesRepositoryJdbcImpl implements LikesRepository{
    @Override
    public Optional<Likes> findById(Long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public void save(Likes entity) throws SQLException {

    }

    @Override
    public void save(List<Likes> entity, int countOfEntities) throws SQLException {

    }

    @Override
    public void update(Likes entity) throws SQLException {

    }

    @Override
    public void remove(Likes entity) throws SQLException {

    }

    @Override
    public void removeById(Long id) throws SQLException {

    }
}
