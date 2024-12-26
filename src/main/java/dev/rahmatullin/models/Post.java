package dev.rahmatullin.models;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Post {
    private Long id;
    private String text;
    private Long userId;
    private Date date;
}
