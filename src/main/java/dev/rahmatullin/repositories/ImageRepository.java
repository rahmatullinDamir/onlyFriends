package dev.rahmatullin.repositories;

import dev.rahmatullin.models.Image;

import java.sql.SQLException;
import java.util.Optional;

public interface ImageRepository extends CrudRepository<Image>{
        Optional<Image> getImageByName(String name) throws SQLException;
}
