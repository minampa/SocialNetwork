package org.example.database;

import org.springframework.stereotype.Component;

import java.util.TreeMap;

@Component
public class ActivationCodeDb {

    public TreeMap<Integer, String> activationCodes = new TreeMap<>();
    private ActivationCodeDb(){

    }
    private static ActivationCodeDb activationCodeDb;

    public static synchronized ActivationCodeDb getInstance(){
        if (activationCodeDb == null){
            activationCodeDb = new ActivationCodeDb();
        }
        return activationCodeDb;
    }
    public void insertActivationCode(Integer id, String activationCode){
        activationCodes.put(id, activationCode);
    }
    public void deleteActivationCode(Integer id){
        activationCodes.remove(id);
    }

}
