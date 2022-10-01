package org.example.database;

import org.example.model.PostModel;

import java.util.*;

public class PostDb {
    private static PostDb instance;
    private TreeMap<Integer, String>  posts;
    private PostDb(){
         posts = new TreeMap<>();
    }
    public static PostDb getInstance(){
        if (instance == null){
            instance = new PostDb();
        }
        return instance;
    }

    public void createPost(PostModel post){

    }

}
