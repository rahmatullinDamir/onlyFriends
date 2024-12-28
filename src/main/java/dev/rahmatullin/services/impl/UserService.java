package dev.rahmatullin.services.impl;

import dev.rahmatullin.models.User;
import dev.rahmatullin.repositories.UserRepository;
import dev.rahmatullin.repositories.impl.UserRepositoryJdbcImpl;
import lombok.*;

import java.sql.SQLException;
import java.util.List;

@Getter
@Setter
@ToString
public class UserService {
    private UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public static void main(String[] args) {
//        userRepository.save();
//    }
//    private List<User> findAll() {
//        return userRepository.
//    }

}
