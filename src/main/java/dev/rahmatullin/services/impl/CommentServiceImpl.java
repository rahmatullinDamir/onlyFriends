package dev.rahmatullin.services.impl;

import dev.rahmatullin.dto.CommentDto;
import dev.rahmatullin.models.Comment;
import dev.rahmatullin.repositories.CommentRepository;
import dev.rahmatullin.services.CommentService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;


    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) throws SQLException {
        List<Comment> comments = commentRepository.getCommentsByPostId(postId);
        List<CommentDto> commentDtoList = new ArrayList<>();

        for(Comment comment: comments) {
            commentDtoList.add(
                    CommentDto.builder()
                            .text(comment.getText())
                            .date(comment.getDate())
                            .userId(comment.getUserId())
                            .build()
            );
        }

        return commentDtoList;
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            if (passwordEncoder.matches(form.getPassword(), user.getPassword())) {
//                return UserDto.builder()
//                        .id(user.getId())
//                        .name(user.getName())
//                        .role(user.getRole())
//                        .build();
    }

    @Override
    public void saveCommentToDb(Comment comment) throws SQLException {
        commentRepository.save(comment);
    }
}
