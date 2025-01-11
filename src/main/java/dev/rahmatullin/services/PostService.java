package dev.rahmatullin.services;

import dev.rahmatullin.dto.PostDto;
import dev.rahmatullin.models.Post;

import java.sql.SQLException;
import java.util.List;

public interface PostService {
    List<PostDto> getPostsForFeed();
    List<String> getImagesForPost(long postId) throws SQLException;
    void addImageToPost(Long postId, Long imageId) throws SQLException;

    void addPost(Post post) throws SQLException;
    void addLike(Long postId, Long likeId) throws SQLException;
    void removeLike(Long postId) throws SQLException;
    int getLikeCount(Long postId) throws SQLException;
    List<PostDto> getPostsWithUserId(Long userId) throws SQLException;
}
