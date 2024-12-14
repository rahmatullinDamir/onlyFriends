package dev.rahmatullin.models;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Likes {
    private Long id;
    private Date date;
}
