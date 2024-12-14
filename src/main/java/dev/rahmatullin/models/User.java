package dev.rahmatullin.models;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    private Long id;
    private int age;
    private String name;
    private String surname;
    private String password;
    private Image avatarImage;
}
