package org.example.model;

import java.util.Date;

public class PostModel {
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
