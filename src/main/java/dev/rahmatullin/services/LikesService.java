package dev.rahmatullin.services;

import dev.rahmatullin.models.Likes;

import java.sql.SQLException;

public interface LikesService {
    Long getLikeIdByPostAndUser(Long postId, Long userId) throws SQLException;
    void addLike(Long postId, Long userId) throws SQLException;
    void removeLike(Long likeId, Long postId, Long userId) throws SQLException;

    Long getLikeCount(Long postId) throws SQLException;

    boolean isPostLikedByUser(Long postId, Long userId) throws SQLException;
}
