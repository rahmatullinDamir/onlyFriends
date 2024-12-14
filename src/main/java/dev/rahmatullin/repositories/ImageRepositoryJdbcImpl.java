package dev.rahmatullin.repositories;

import dev.rahmatullin.models.Image;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ImageRepositoryJdbcImpl implements ImageRepository {
    @Override
    public Optional<Image> findById(Long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public void save(Image entity) throws SQLException {

    }

    @Override
    public void save(List<Image> entity, int countOfEntities) throws SQLException {

    }

    @Override
    public void update(Image entity) throws SQLException {

    }

    @Override
    public void remove(Image entity) throws SQLException {

    }

    @Override
    public void removeById(Long id) throws SQLException {

    }
}
