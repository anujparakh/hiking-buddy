package com.example.hikingbuddy.friend;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.hikingbuddy.common.constants.FriendStatus;
import com.example.hikingbuddy.user.UserDto;
import com.example.hikingbuddy.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Autowired
    public FriendService(FriendRepository friendRepository, UserRepository userRepository) {
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    public List<FriendDto> getAllFriends() {
        return friendRepository.findAll().stream().map(friend -> new FriendDto(friend)).collect(Collectors.toList());
    }

    public List<FriendDto> getIncomingFriendsForUser(Long userID) {
        return friendRepository.findByTargetUser_idAndFriendStatus(userID, FriendStatus.REQUESTED).stream()
                .map(friend -> new FriendDto(friend)).collect(Collectors.toList());
    }

    public List<FriendDto> getOutgoingFriendsForUser(Long userID) {
        return friendRepository.findBySourceUser_idAndFriendStatus(userID, FriendStatus.REQUESTED).stream()
                .map(friend -> new FriendDto(friend)).collect(Collectors.toList());
    }

    public List<FriendDto> getAcceptedFriendsForUser(Long userID) {
        List<Friend> friendsIncoming = friendRepository.findByTargetUser_idAndFriendStatus(userID,
                FriendStatus.ACCEPTED);
        List<Friend> friendsOutgoing = friendRepository.findBySourceUser_idAndFriendStatus(userID,
                FriendStatus.ACCEPTED);
        return Stream.concat(friendsIncoming.stream(), friendsOutgoing.stream()).map(friend -> new FriendDto(friend))
                .collect(Collectors.toList());
    }

    public Optional<FriendDto> getFriend(Long user1ID, Long user2ID) {
        Friend friendOut = friendRepository.findBySourceUser_idAndTargetUser_id(user1ID, user2ID).orElse(null);
        if (friendOut != null)
            return Optional.ofNullable(new FriendDto(friendOut));
        Friend friendIn = friendRepository.findBySourceUser_idAndTargetUser_id(user1ID, user2ID).orElse(null);
        if (friendIn != null)
            return Optional.ofNullable(new FriendDto(friendIn));

        return Optional.empty();
    }

    public FriendDto createFriend(Long sourceUserID, Long targetUserID) {
        // Check if friendship already exists
        FriendDto existing = getFriend(sourceUserID, targetUserID).orElse(null);
        if(existing != null)
            return existing;
        Friend createdFriend = friendRepository
                .save(new Friend(userRepository.getById(sourceUserID), userRepository.getById(targetUserID)));
        return new FriendDto(createdFriend);
    }

    public FriendDto acceptFriend(Long sourceUserID, Long targetUserID) {
        Friend friend = friendRepository.findBySourceUser_idAndTargetUser_id(sourceUserID, targetUserID).orElse(null);
        if (friend == null)
            return null;

        friend.setFriendStatus(FriendStatus.ACCEPTED);
        return new FriendDto(friendRepository.save(friend));
    }

    public FriendDto deleteFriend(Long sourceUserID, Long targetUserID) {
        Friend friend = friendRepository.findBySourceUser_idAndTargetUser_id(sourceUserID, targetUserID).orElse(null);
        if (friend != null) {
            friendRepository.delete(friend);
            return new FriendDto(friend);
        }

        friend = friendRepository.findBySourceUser_idAndTargetUser_id(targetUserID, sourceUserID).orElse(null);
        if (friend != null) {
            friendRepository.delete(friend);
            return new FriendDto(friend);
        }

        return null;
    }

}