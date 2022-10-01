package org.example.database;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.TreeMap;

@Component

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

    public void insertToken(Integer id, String token){
        tokens.put(id, token);
    }
    public void deleteToken(Integer id){
        tokens.remove(id);
    }
}
