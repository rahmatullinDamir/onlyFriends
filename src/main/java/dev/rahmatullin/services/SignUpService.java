package dev.rahmatullin.services;

import dev.rahmatullin.dto.SignUpForm;

import java.sql.SQLException;

public interface SignUpService {

    void signUp(SignUpForm form) throws SQLException;

}
