package dev.rahmatullin.models;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class User {
    private Long id;
    private Integer age;
    private String name;
    private String surname;
    private String password;
    private String role;
    private Long avatarImageId;
    private String email;
}


