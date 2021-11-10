package com.example.hikingbuddy.friend;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.example.hikingbuddy.common.constants.FriendStatus;
import com.example.hikingbuddy.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "source_id", "target_id" }) })
public class Friend {
    private @Id @GeneratedValue Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    private User sourceUser;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id")
    private User targetUser;
    private Integer friendStatus;
    private Date dateRequested;

    // Friend request for right now
    public Friend(User sourceUser, User targetUser) {
        this.sourceUser = sourceUser;
        this.targetUser = targetUser;
        this.friendStatus = FriendStatus.REQUESTED;
        this.dateRequested = new Date();
    }

    public Friend() {
    }
}