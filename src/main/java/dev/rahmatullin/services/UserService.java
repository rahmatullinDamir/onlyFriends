package dev.rahmatullin.services;

import dev.rahmatullin.models.User;
import dev.rahmatullin.repositories.DBConnection;
import dev.rahmatullin.repositories.UserRepository;
import dev.rahmatullin.repositories.UserRepositoryJdbcImpl;
import lombok.*;

import java.sql.SQLException;

@Getter
@Setter
@ToString
public class UserService {
    static UserRepository userRepository = new UserRepositoryJdbcImpl(DBConnection.getInstance().getConnection());

    public static void main(String[] args) throws SQLException {
        System.out.println(userRepository.findById(3L).get());
    }

}
