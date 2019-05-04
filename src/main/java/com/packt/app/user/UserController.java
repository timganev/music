package com.packt.app.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value="/users")
    public Iterable<User> listUser(){
        return userService.findAll();
    }

    @PostMapping(value="/signup")
    public User saveUser(@RequestBody UserDto user){
        return userService.save(user);
    }



}