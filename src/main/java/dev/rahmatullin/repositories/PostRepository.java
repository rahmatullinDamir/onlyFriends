package dev.rahmatullin.repositories;

import dev.rahmatullin.dto.PostDto;
import dev.rahmatullin.models.Image;
import dev.rahmatullin.models.Post;

import java.sql.SQLException;
import java.util.List;

public interface PostRepository extends CrudRepository<Post>{
    List<PostDto> getPostsWithUserId(Long userId) throws SQLException;
    void addImageToPost(Long postId, Long imageId) throws SQLException;
    void addLike(Long postId, Long likeId) throws SQLException;
    void removeLike(Long postId) throws SQLException;
    int getLikeCount(Long postId) throws SQLException;
    List<String> getImagesForPost(long postId) throws SQLException;
}
