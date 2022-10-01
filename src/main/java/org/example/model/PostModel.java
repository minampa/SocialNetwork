package org.example.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class PostModel {
    @Id
    public int id;
    public String description;
    public Date creationDate;

    public PostModel(){

    }


    public PostModel(int id, String description, Date creationDate) {
        this.id = id;
        this.description = description;
        this.creationDate = creationDate;
    }
}
