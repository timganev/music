package com.packt.app.userDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.packt.app.playlist.Playlist;
import com.packt.app.user.User;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 2,max = 25,message = "First Name must be between 2 and 25 symbols")
    private String firstName;

    @Size(min = 2,max = 25,message = "Last Name must be between 2 and 25 symbols")
    private String lastName;

    @Size(min = 2,max = 50,message = "Email must be between 2 and 50 symbols")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user_id;


    public UserDetails(String firstName, String lastName, String email) {
       setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
    }

    public UserDetails() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@Valid String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(@Valid String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@Valid String email) {
        this.email = email;
    }
}
