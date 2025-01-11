package dev.rahmatullin.repositories;

import dev.rahmatullin.models.Friends;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface FriendsRepository extends CrudRepository<Friends> {
    List<Friends> getFriends(Long userId) throws SQLException;

    List<Friends> getFriendsLimit(Long userId, int limit) throws SQLException;

    List<Friends> getPendingRequests(Long userId) throws SQLException;

    Optional<Friends> getFriendshipByUser(Long user_id, Long friend_id) throws SQLException;
}
