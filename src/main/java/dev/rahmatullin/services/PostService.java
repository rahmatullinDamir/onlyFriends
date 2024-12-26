package dev.rahmatullin.services;

import dev.rahmatullin.repositories.DBConnection;
import dev.rahmatullin.repositories.PostRepository;
import dev.rahmatullin.repositories.PostRepositoryJdbcImpl;

public class PostService {
    static PostRepository postRepository = new PostRepositoryJdbcImpl(DBConnection.getInstance().getConnection());
}
