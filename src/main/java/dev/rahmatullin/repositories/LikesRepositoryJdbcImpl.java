package dev.rahmatullin.repositories;

import dev.rahmatullin.models.Likes;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class LikesRepositoryJdbcImpl implements LikesRepository {
    private Connection connection;

    private static final String SQL_SELECT_ALL_FROM_LIKES = "SELECT * FROM likes";
    private static final String SQL_INSERT_TO_LIKES = "INSERT INTO likes (date) VALUES (?)";
    private static final String SQL_UPDATE_LIKES = "UPDATE likes SET date = ? WHERE id = ?";
    private static final String SQL_DELETE_LIKES = "DELETE FROM likes WHERE id = ?";

    public LikesRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Likes> findById(Long id) throws SQLException {
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
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_LIKES);
        statement.setLong(1, id);
        statement.executeUpdate();
    }
}