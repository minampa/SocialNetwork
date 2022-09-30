package org.example.database;

import java.util.TreeMap;

public class TokenDb {
    public TreeMap<Integer,String> tokens = new TreeMap<>();
    private TokenDb(){

    }

    private static TokenDb tokenDb;

    public static synchronized TokenDb getInstance(){
        if (tokenDb == null){
            tokenDb = new TokenDb();
        }
        return tokenDb;
    }
}
