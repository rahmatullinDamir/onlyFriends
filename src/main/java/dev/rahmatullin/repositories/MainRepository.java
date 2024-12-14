package dev.rahmatullin.repositories;
import java.sql.Connection;
import java.sql.DriverManager;

public class MainRepository {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "admin";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/onlyFriendsDb";

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        UserRepository userRepository = new UserRepositoryJdbcImpl(connection);


    }
}
