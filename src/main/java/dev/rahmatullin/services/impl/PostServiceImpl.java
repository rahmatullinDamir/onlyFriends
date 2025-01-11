package dev.rahmatullin.services.impl;

import dev.rahmatullin.dto.PostDto;
import dev.rahmatullin.models.Post;
import dev.rahmatullin.repositories.PostRepository;
import dev.rahmatullin.services.PostService;

import java.sql.SQLException;
import java.util.List;

public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<PostDto> getPostsForFeed() {
        return null;
    }

    @Override
    public List<String> getImagesForPost(long postId) throws SQLException {
        return postRepository.getImagesForPost(postId);
    }

    @Override
    public void addImageToPost(Long postId, Long imageId) throws SQLException {
        postRepository.addImageToPost(postId, imageId);
    }

    @Override
    public void addPost(Post post) throws SQLException {
        postRepository.save(post);
    }

    @Override
    public void addLike(Long postId, Long likeId) throws SQLException {
        postRepository.addLike(postId, likeId);
    }

    @Override
    public void removeLike(Long postId) throws SQLException {
        postRepository.removeLike(postId);
    }

    @Override
    public int getLikeCount(Long postId) throws SQLException {
        return postRepository.getLikeCount(postId);
    }

    @Override
    public List<PostDto> getPostsWithUserId(Long userId) throws SQLException {
        return postRepository.getPostsWithUserId(userId);
    }
}
