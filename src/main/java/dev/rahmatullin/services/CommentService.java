package dev.rahmatullin.services;

import dev.rahmatullin.repositories.CommentRepository;
import dev.rahmatullin.repositories.CommentRepositoryJdbcImpl;
import dev.rahmatullin.repositories.DBConnection;

public class CommentService {
    static CommentRepository commentRepository = new CommentRepositoryJdbcImpl(DBConnection.getInstance().getConnection());
}
