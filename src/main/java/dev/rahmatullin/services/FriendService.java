package dev.rahmatullin.services;

import dev.rahmatullin.dto.FriendDto;
import dev.rahmatullin.dto.FriendsDto;
import dev.rahmatullin.models.Friends;

import java.sql.SQLException;
import java.util.List;

public interface FriendService {
    void acceptFriendRequest(FriendsDto friendsDto) throws SQLException;
    void rejectFriendRequest(FriendsDto friendsDto) throws SQLException;
    void removeFriend(Friends friendsDto) throws SQLException;
    Friends getFriend(Long userId, Long friendId) throws SQLException;
    String isFriends(Long userId, Long friendId) throws SQLException;
    List<FriendDto> getFriends(Long userId) throws SQLException;
    List<FriendDto> getPendingRequest(Long userId) throws SQLException;
}
