package dev.rahmatullin.services;

import dev.rahmatullin.dto.SignInForm;
import dev.rahmatullin.dto.UserDto;

import java.sql.SQLException;

public interface SignInService {
    UserDto signIn(SignInForm from) throws SQLException;
}
