package dev.rahmatullin.services;

import dev.rahmatullin.dto.CommentDto;
import dev.rahmatullin.models.Comment;

import java.sql.SQLException;
import java.util.List;

public interface CommentService {
    List<CommentDto> getCommentsByPostId(Long postId) throws SQLException;

    void saveCommentToDb(Comment comment) throws SQLException;
    void deleteComment(Long id) throws SQLException;
}
