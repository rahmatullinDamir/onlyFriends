package dev.rahmatullin.models;
import lombok.*;

import javax.enterprise.inject.Stereotype;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Post {
    private Long id;
    private String text;
    private String title;
    private Long userId;
    private Date date;
}
