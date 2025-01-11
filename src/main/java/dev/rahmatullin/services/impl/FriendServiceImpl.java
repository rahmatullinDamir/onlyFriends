package dev.rahmatullin.services.impl;

import dev.rahmatullin.dto.FriendDto;
import dev.rahmatullin.dto.FriendsDto;
import dev.rahmatullin.models.Friends;
import dev.rahmatullin.models.User;
import dev.rahmatullin.repositories.FriendsRepository;
import dev.rahmatullin.repositories.UserRepository;
import dev.rahmatullin.services.FriendService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FriendServiceImpl implements FriendService {

    private FriendsRepository friendsRepository;
    private UserRepository userRepository;

    public FriendServiceImpl(FriendsRepository friendsRepository, UserRepository userRepository) {
        this.friendsRepository = friendsRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void acceptFriendRequest(FriendsDto FriendsDto) throws SQLException {
        Optional<Friends> friendshipOptional = friendsRepository.getFriendshipByUser(FriendsDto.getUserId(), FriendsDto.getFriendId());
        if (friendshipOptional.isPresent()) {
            Friends friends = friendshipOptional.get();
            if (friends.getStatus().equals("PENDING")) {
                friendsRepository.update(Friends.builder()
                        .id(friends.getId())
                        .userId(FriendsDto.getUserId())
                        .friendId(FriendsDto.getFriendId())
                        .status("ACCEPTED")
                        .build());
            }
        }
    }

    @Override
    public void rejectFriendRequest(FriendsDto FriendsDto) throws SQLException {
        Optional<Friends> friendsOptional = friendsRepository.getFriendshipByUser(FriendsDto.getUserId(), FriendsDto.getFriendId());
        if (friendsOptional.isPresent()) {
            Friends friends = friendsOptional.get();
            friendsRepository.removeById(friends.getId());
        }
    }

    @Override
    public void removeFriend(Friends friends) throws SQLException {
        friendsRepository.remove(friends);
    }

    @Override
    public Friends getFriend(Long userId, Long friendId) throws SQLException {
        Optional<Friends> friendsOptional = friendsRepository.getFriendshipByUser(userId, friendId);
        if (friendsOptional.isPresent()) {
            return friendsOptional.get();
        }
        return null;
    }

    public String isFriends(Long userId, Long friendId) throws SQLException {
        Optional<Friends> friends = friendsRepository.getFriendshipByUser(userId, friendId);
        if(friends.isPresent()) {
            Friends friend = friends.get();
            if (friend.getStatus().equals("ACCEPTED")) {
                return "ACCEPTED";
            }
            return "PENDING";
        }
        return "NONE";
    }

    @Override
    public List<FriendDto> getFriends(Long userId) throws SQLException {
        List<FriendDto> allFriends = new ArrayList<>();
        List<Friends> friends = friendsRepository.getFriends(userId);
        for (Friends Friends : friends) {
            Optional<User> userOptional;
            if (!Friends.getUserId().equals(userId)) {
                userOptional = userRepository.findById(Friends.getUserId());
            } else {
                userOptional = userRepository.findById(Friends.getFriendId());
            }
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                allFriends.add(new FriendDto(user.getId(), user.getName(), user.getSurname(), user.getUsername()));
            }
        }
        return allFriends;
    }

    @Override
    public List<FriendDto> getPendingRequest(Long userId) throws SQLException {
        List<FriendDto> allRequest = new ArrayList<>();
        List<Friends> friends = friendsRepository.getPendingRequests(userId);
        for (Friends Friends : friends) {
            Optional<User> userOptional = userRepository.findById(Friends.getUserId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                allRequest.add(new FriendDto(user.getId(), user.getName(), user.getSurname(), user.getUsername()));
            }
        }
        return allRequest;
    }


}
