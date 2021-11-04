package com.example.hikingbuddy.friend;

import java.security.Principal;
import java.util.List;

import com.example.hikingbuddy.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/friends")
class FriendController {
    @Autowired
    private FriendService friendService;

    // Mainly for testing
    @GetMapping("")
    public ResponseEntity<List<FriendDto>> getAllFriends() {
        return ResponseEntity.ok(friendService.getAllFriends());
    }

    // Get Incoming Friend Requests
    @GetMapping("/incoming")
    public ResponseEntity<List<FriendDto>> getIncomingFriends(Principal principal) {
        return ResponseEntity.ok(friendService.getIncomingFriendsForUser(Long.valueOf(principal.getName())));
    }

    // Get Outgoing Friend Requests
    @GetMapping("/outgoing")
    public ResponseEntity<List<FriendDto>> getOutgoingFriends(Principal principal) {

        return ResponseEntity.ok(friendService.getOutgoingFriendsForUser(Long.valueOf(principal.getName())));
    }

    // Get Accepted Friends
    @GetMapping("/accepted")
    public ResponseEntity<List<FriendDto>> getAcceptedFriends(Principal principal) {

        return ResponseEntity.ok(friendService.getAcceptedFriendsForUser(Long.valueOf(principal.getName())));
    }

    // Get Friend Info
    @GetMapping("/{friend_id}")
    public ResponseEntity<FriendDto> getFriendInfo(@PathVariable(value = "friend_id") Long friendId,
            Principal principal) {

        FriendDto friend = friendService.getFriend(Long.valueOf(principal.getName()), friendId).orElse(null);
        if (friend == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(friend);
    }

    // Create Friend Request
    @PostMapping("/{friend_id}")
    public ResponseEntity<FriendDto> createFriend(@PathVariable(value = "friend_id") Long friendId,
            Principal principal) {

        return ResponseEntity.ok(friendService.createFriend(Long.valueOf(principal.getName()), friendId));
    }

    // Accpet Friend Request
    @PutMapping("/{friend_id}")
    public ResponseEntity<FriendDto> acceptFriend(@PathVariable(value = "friend_id") Long friendId,
            Principal principal) {
        FriendDto toReturn = friendService.acceptFriend(friendId, Long.valueOf(principal.getName()));
        if (toReturn == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(toReturn);

    }

    // Decline Friend Request/Remove Friend
    @DeleteMapping("/{friend_id}")
    public ResponseEntity<FriendDto> deleteFriend(@PathVariable(value = "friend_id") Long friendId,
            Principal principal) {
        FriendDto toReturn = friendService.deleteFriend(Long.valueOf(principal.getName()), friendId);
        if (toReturn == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(toReturn);
    }

}