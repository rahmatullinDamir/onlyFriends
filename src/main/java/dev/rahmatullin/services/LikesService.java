package dev.rahmatullin.services;

import dev.rahmatullin.repositories.*;

public class LikesService {
    static LikesRepository likesRepository = new LikesRepositoryJdbcImpl(DBConnection.getInstance().getConnection());
}
