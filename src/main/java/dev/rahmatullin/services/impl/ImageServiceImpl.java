package dev.rahmatullin.services.impl;

import dev.rahmatullin.models.Image;
import dev.rahmatullin.repositories.ImageRepository;
import dev.rahmatullin.services.ImageService;

import java.sql.SQLException;
import java.util.Optional;

public class ImageServiceImpl implements ImageService {
    private ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    @Override
    public void saveImageToDb(Image image) throws SQLException {
        imageRepository.save(image);
    }

    @Override
    public Image getImageFromDbById(Long id) throws SQLException {
        Optional<Image> imageOptional = imageRepository.findById(id);
        if(imageOptional.isPresent()) {
            return imageOptional.get();
        }
        return null;
    }

    @Override
    public Image getImageFromDbByName(String name) throws SQLException {
        Optional<Image> imageOptional = imageRepository.getImageByName(name);
        if(imageOptional.isPresent()) {
            return imageOptional.get();
        }
        return null;
    }
}
