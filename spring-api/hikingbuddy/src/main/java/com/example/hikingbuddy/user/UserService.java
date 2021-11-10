package com.example.hikingbuddy.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserService implements UserDetailsService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(user -> {
            return modelMapper.map(user, UserDto.class);
        }).collect(Collectors.toList());
    }

    public Optional<UserDto> getUserById(long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            return Optional.empty();
        else
            return Optional.ofNullable(modelMapper.map(user, UserDto.class));
    }

    public Optional<UserDto> getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null)
            return Optional.empty();
        else
            return Optional.ofNullable(modelMapper.map(user, UserDto.class));
    }

    public UserDto createUser(User newUser) {
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        return modelMapper.map(userRepository.save(newUser), UserDto.class);
    }

    // Update User
    public UserDto updateUser(UserDto updatedUser) {
        return modelMapper.map(userRepository.save(modelMapper.map(updatedUser, User.class)), UserDto.class);
    }

    // Delete User
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    // For UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        User user = userRepository.findByEmail(email).orElse(null);
        if(user == null) {
            throw new UsernameNotFoundException(email);
        }

        return user;
    }
}