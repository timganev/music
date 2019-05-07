package com.packt.app.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    @Autowired //constr
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value="/users")
    public Iterable<User> listUser(){
        return userService.findAll();
    }

    @PostMapping(value="/signup")
    public ResponseEntity<User> saveUser(@RequestBody UserDto user){
        if (userService.findOne(user.getUsername())!=null) {
           return new ResponseEntity<>(new User(), HttpStatus.IM_USED);
        }
       return new  ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }



}