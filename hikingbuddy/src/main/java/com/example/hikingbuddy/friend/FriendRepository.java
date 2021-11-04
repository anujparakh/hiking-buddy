package com.example.hikingbuddy.friend;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findBySourceUser_id(Long id);
    List<Friend> findByTargetUser_id(Long id);
    List<Friend> findByTargetUser_idAndFriendStatus(Long id, Integer FriendStatus);
    List<Friend> findBySourceUser_idAndFriendStatus(Long id, Integer FriendStatus);

    Optional<Friend> findBySourceUser_idAndTargetUser_id(Long sourceId, Long targetId);
}