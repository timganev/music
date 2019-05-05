package com.packt.app.userTests;

import com.packt.app.album.Album;
import com.packt.app.album.AlbumRepository;
import com.packt.app.album.AlbumService;
import com.packt.app.album.AlbumServiceImpl;
import com.packt.app.artist.Artist;
import com.packt.app.artist.ArtistRepository;
import com.packt.app.artist.ArtistService;
import com.packt.app.artist.ArtistServiceImpl;
import com.packt.app.user.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
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

    @Test
    public void deleteUserById_Should_Call_UserRepository_deleteById(){
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserServiceImpl userService=new UserServiceImpl(userRepository);

        User user=new User("name","password","role");
        user.setId(1);
        userService.delete(1);
        //Assert
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(1);
    }

    @Test(expected = NullPointerException.class)
    public void finOneUser_Should_Return_User_By_Username() {

        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserServiceImpl userService=new UserServiceImpl(userRepository);

        UserDto user=new UserDto("name","123456789","role");
        userService.save(user);

        User user1=userService.findOne("name");

        Mockito.when(userService.findOne("name"))
                .thenReturn(
                        user1
                );

        //Act
        User result = userService.findOne("name");

        //Assert
        Assert.assertEquals("name", result.getUsername());

    }


}
