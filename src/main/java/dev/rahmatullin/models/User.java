package dev.rahmatullin.models;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class User {
    private Long id;
    private int age;
    private String name;
    private String surname;
    private String password;
    private Long avatarImageId;


}


