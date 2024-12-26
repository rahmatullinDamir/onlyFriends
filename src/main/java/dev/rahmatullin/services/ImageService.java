package dev.rahmatullin.services;

import dev.rahmatullin.repositories.DBConnection;
import dev.rahmatullin.repositories.ImageRepository;
import dev.rahmatullin.repositories.ImageRepositoryJdbcImpl;

public class ImageService {
    static ImageRepository imageRepository = new ImageRepositoryJdbcImpl(DBConnection.getInstance().getConnection());
}
