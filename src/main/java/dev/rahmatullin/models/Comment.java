package dev.rahmatullin.models;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@Builder

public class Comment {
    private Long id;
    private String text;
    private Long userId;
    private Date date;
}
