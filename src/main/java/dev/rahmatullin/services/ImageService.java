package dev.rahmatullin.services;

import dev.rahmatullin.models.Image;

import java.sql.SQLException;

public interface ImageService {
    void saveImageToDb(Image image) throws SQLException;
    Image getImageFromDbById(Long id) throws SQLException;
    Image getImageFromDbByName(String name) throws SQLException;
}
