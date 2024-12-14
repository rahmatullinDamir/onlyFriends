package dev.rahmatullin.repositories;

import dev.rahmatullin.models.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ImageRepositoryJdbcImpl implements ImageRepository {
    private Connection connection;

    private static final String SQL_SELECT_ALL_FROM_IMAGE = "SELECT * FROM image";
    private static final String SQL_INSERT_TO_IMAGE = "INSERT INTO image (name, url, extension) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_IMAGE = "UPDATE image SET name = ?, url = ?, extension = ? WHERE id = ?";
    private static final String SQL_DELETE_IMAGE = "DELETE FROM image WHERE id = ?";

    ImageRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Image> findById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_FROM_IMAGE + " WHERE id = ?");
        statement.setLong(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return Optional.of(new Image(resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("url"),
                    resultSet.getString("extension")));
        }

        return Optional.empty();
    }

    @Override
    public void save(Image entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_INSERT_TO_IMAGE);

        statement.setString(1, entity.getName());
        statement.setString(2, entity.getUrl());
        statement.setString(3, entity.getExtension());

        statement.executeUpdate();
    }

    @Override
    public void save(List<Image> entities, int countOfEntities) throws SQLException {
        for (int i = 0; i < countOfEntities; i++) {
            save(entities.get(i));
        }
    }

    @Override
    public void update(Image entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_IMAGE);

        statement.setString(1, entity.getName());
        statement.setString(2, entity.getUrl());
        statement.setString(3, entity.getExtension());
        statement.setLong(4, entity.getId());

        statement.executeUpdate();
    }

    @Override
    public void remove(Image entity) throws SQLException {
        removeById(entity.getId());
    }

    @Override
    public void removeById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_IMAGE);

        statement.setLong(1, id);
        statement.executeUpdate();
    }
}