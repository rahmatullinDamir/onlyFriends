package dev.rahmatullin.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String role;
    private Long avatarImageId;
    private int age;
    private String surname;
    private String username;


    public boolean isAdmin() {
        return this.role.equals("ADMIN");
    }
}
