package org.example.model;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class TokenModel {
    public String token;
    @Id
    public int userId;

    public TokenModel(){

    }
    public TokenModel(String token, Integer userId){
        this.token = token;
        this.userId = userId;
    }

}
