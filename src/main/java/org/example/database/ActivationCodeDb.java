package org.example.database;

import java.util.TreeMap;

public class ActivationCodeDb {

    public TreeMap<Integer, String> activationCode = new TreeMap<>();
    private ActivationCodeDb(){

    }
    private static ActivationCodeDb activationCodeDb;

    public static synchronized ActivationCodeDb getInstance(){
        if (activationCodeDb == null){
            activationCodeDb = new ActivationCodeDb();
        }
        return activationCodeDb;
    }

}
