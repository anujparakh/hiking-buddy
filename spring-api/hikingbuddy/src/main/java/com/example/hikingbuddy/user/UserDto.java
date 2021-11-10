package com.example.hikingbuddy.user;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String email;
    private String fullName;
    private Date dateOfBirth;
    private Integer SkillLevel;
    private String phoneNumber;
    private boolean pet;
    private String location;
    private String description;
    private String experience;

    public UserDto() {
    };
}