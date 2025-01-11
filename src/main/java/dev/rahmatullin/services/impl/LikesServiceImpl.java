package dev.rahmatullin.services.impl;

import dev.rahmatullin.models.Likes;
import dev.rahmatullin.repositories.*;
import dev.rahmatullin.services.LikesService;

import java.sql.SQLException;

public class LikesServiceImpl implements LikesService {
    private LikesRepository likesRepository;

    public LikesServiceImpl(LikesRepository likesRepository) {
        this.likesRepository = likesRepository;
    }


    @Override
    public Long getLikeIdByPostAndUser(Long postId, Long userId) throws SQLException {
        return likesRepository.getLikeIdByPostAndUser(postId, userId);
    }

    @Override
    public void addLike(Long postId, Long userId) throws SQLException {
        likesRepository.addLike(postId, userId);
    }

    @Override
    public void removeLike(Long likeId, Long postId, Long userId) throws SQLException {
        likesRepository.removeLike(likeId, postId, userId);
    }

    @Override
    public Long getLikeCount(Long postId) throws SQLException {
        return likesRepository.getLikeCount(postId);
    }

    @Override
    public boolean isPostLikedByUser(Long postId, Long userId) throws SQLException {
        return likesRepository.isPostLikedByUser(postId, userId);
    }


}
