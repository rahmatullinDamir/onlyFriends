package dev.rahmatullin.repositories;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    Optional<T> findById(Long id) throws SQLException;

    void save(T entity) throws SQLException;

    void save(List<T> entity, int countOfEntities) throws SQLException;

    void update(T entity) throws SQLException;

    void remove(T entity) throws SQLException;

    void removeById(Long id) throws SQLException;
}