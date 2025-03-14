package com.example.springRest.repo;

import com.example.springRest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;




@Repository
public interface UserRepo extends JpaRepository<User, String> {

    User findByUsername(String username);


    boolean existsByUsername(String username);
}