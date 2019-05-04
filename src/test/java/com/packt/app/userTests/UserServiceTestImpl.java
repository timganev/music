package com.packt.app.userTests;

import com.packt.app.artist.Artist;
import com.packt.app.artist.ArtistRepository;
import com.packt.app.artist.ArtistService;
import com.packt.app.artist.ArtistServiceImpl;
import com.packt.app.user.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class UserServiceTestImpl {

    @Test
    public void getUsers_Should_Return_All_Existing_Users() {

        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserServiceImpl userService=new UserServiceImpl(userRepository);

        User user=new User("name","password","role");

        List<User> list = new ArrayList<>();
        list.add(user);


        Mockito.when(userService.findAll())
                .thenReturn(
                        list
                );

        //Act
        List<User> result = (List<User>) userService.findAll();

        //Assert
        Assert.assertEquals(1, result.size());

    }
}
