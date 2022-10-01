package org.example.model;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class ActivationModel {
    @Id
    public int userId;
    public String activationCode;
    public ActivationModel(){

    }
    public ActivationModel(int id,String activationCode){
        this.userId = id;
        this.activationCode = activationCode;
    }
}
