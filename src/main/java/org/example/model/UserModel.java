package org.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "UserModel")
public class UserModel extends ResponseModel {
    @Id
    public int id;

    public String firstName;

    public String lastName;
    @Column(unique = true)

    public String username;
    public String password;
    @Column(unique = true)

    public String phoneNumber;

    public boolean isActive;
    public UserModel(){

    }

    public UserModel(int id, String firstName, String lastName, String username,
                     String password ,String phoneNumber, boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.isActive = isActive;
    }

}
