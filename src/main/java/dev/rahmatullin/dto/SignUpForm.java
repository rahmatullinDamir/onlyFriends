package dev.rahmatullin.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpForm {
    private String email;
    private Integer age;
    private String name;
    private String surname;
    private String password;
    private String username;
}
