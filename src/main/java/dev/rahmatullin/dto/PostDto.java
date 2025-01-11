package dev.rahmatullin.dto;


import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class PostDto {
    private Long id;
    private Long userId;
    private String title;
    private String text;
    private Date date;
    private List<String> images;

    private Long likeCount;
    private boolean userLiked;
}
