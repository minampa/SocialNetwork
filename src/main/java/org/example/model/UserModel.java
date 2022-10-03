package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "UserModel")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String firstName;

    private String lastName;
    @Column(unique = true)

    private String username;
    private String password;
    @Column(unique = true)

    private String phoneNumber;

    private boolean isActive;

}
