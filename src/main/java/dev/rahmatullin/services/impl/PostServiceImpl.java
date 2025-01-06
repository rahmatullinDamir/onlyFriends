package dev.rahmatullin.services.impl;

import dev.rahmatullin.dto.PostDto;
import dev.rahmatullin.models.Post;
import dev.rahmatullin.repositories.PostRepository;
import dev.rahmatullin.services.PostService;

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
    public void savePost(Post post) {

    }
}
