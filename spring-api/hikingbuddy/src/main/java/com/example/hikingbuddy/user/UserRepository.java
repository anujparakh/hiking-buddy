package com.example.hikingbuddy.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /*
     * NOTE: by extending JpaRepository, we "automagically" get some basic DB CRUD
     * functionalities built in. See JPA docs for details. TODO: add any needed
     * custom repository methods
     */
    Optional<User> findByEmail(String email);
}