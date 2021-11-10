package com.example.hikingbuddy.friend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import com.example.hikingbuddy.user.UserDto;

import org.modelmapper.ModelMapper;

@Getter
@Setter
@AllArgsConstructor
public class FriendDto {
    private Long id;
    private UserDto sourceUser;
    private UserDto targetUser;
    private Integer friendStatus;
    private Date dateRequested;

    public FriendDto(Friend friend)
    {
        this.id = friend.getId();
        ModelMapper mapper = new ModelMapper();
        this.sourceUser = mapper.map(friend.getSourceUser(), UserDto.class);
        this.targetUser = mapper.map(friend.getTargetUser(), UserDto.class);
        this.friendStatus = friend.getFriendStatus();
        this.dateRequested = friend.getDateRequested();
    }
}