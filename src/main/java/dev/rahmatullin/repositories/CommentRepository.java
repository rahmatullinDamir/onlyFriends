package dev.rahmatullin.repositories;

import dev.rahmatullin.models.Comment;

import java.sql.SQLException;
import java.util.List;

public interface CommentRepository extends CrudRepository<Comment>{
    List<Comment> getCommentsByPostId(Long postId) throws SQLException;
}

