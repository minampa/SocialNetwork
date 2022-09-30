package org.example.model;

public class UserModel extends ResponseModel {

    public int id;

    public String firstName;

    public String lastName;

    public String username;
    public String password;

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
