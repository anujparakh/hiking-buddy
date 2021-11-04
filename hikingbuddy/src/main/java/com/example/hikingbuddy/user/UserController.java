package com.example.hikingbuddy.user;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(value = "id") long userId) {
        UserDto user = userService.getUserById(userId).orElse(null);
        if (user != null) {
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(Principal principal) {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/user")
    public ResponseEntity<UserDto> createNewUser(@RequestBody User newUser) {
        UserDto user = userService.createUser(newUser);
        return ResponseEntity.ok(user);
    }

    @PutMapping("")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto updatedUser, Principal principal) {
        updatedUser.setId(Long.valueOf(principal.getName()));
        return ResponseEntity.ok(userService.updateUser(updatedUser));
    }

    @DeleteMapping("")
    public ResponseEntity<UserDto> updateUser(Principal principal) {
        userService.deleteUser(Long.valueOf(principal.getName()));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getLoggedInUser(Principal principal) {
        UserDto toReturn = userService.getUserById(Long.valueOf(principal.getName())).orElse(null);

        if (toReturn == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(toReturn);

    }

}