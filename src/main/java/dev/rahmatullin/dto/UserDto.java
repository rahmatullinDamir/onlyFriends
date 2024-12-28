package dev.rahmatullin.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String role;


    public boolean isAdmin() {
        return this.role.equals("ADMIN");
    }
}
