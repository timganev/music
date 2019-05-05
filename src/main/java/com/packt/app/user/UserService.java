package com.packt.app.user;


import java.util.List;

public interface UserService {

    User save(UserDto user);

    List<User> findAll();

    void delete(int id);

    User findOne(String username);

}
