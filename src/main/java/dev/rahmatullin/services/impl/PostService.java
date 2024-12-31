package dev.rahmatullin.services.impl;

import dev.rahmatullin.repositories.PostRepository;

import java.sql.Connection;

public class PostService {
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;

    }
}
