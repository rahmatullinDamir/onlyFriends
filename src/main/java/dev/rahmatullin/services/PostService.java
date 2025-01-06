package dev.rahmatullin.services;

import dev.rahmatullin.dto.PostDto;
import dev.rahmatullin.models.Post;

import java.util.List;

public interface PostService {
    List<PostDto> getPostsForFeed();

    void savePost(Post post);

}
