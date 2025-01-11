package dev.rahmatullin.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CommentDto {
        private Long id;
        private Long userId;
        private String text;
        private Date date;
        private String author;
        private Long postId;
}
