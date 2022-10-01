package org.example.database;

import org.example.model.UserModel;

import java.util.List;
import java.util.TreeMap;

public class UserDb {
    private static UserDb instance = null;
    int counter = 0 ;
    private TreeMap<Integer, UserModel> usersById = new TreeMap<>();
    private TreeMap<String,UserModel> usersByUsername = new TreeMap<>();
    private TreeMap<String,UserModel> usersByPhoneNumbers = new TreeMap<>();
    private UserDb(){
        usersById.put(0,new UserModel(0,"mina","ghorbani","minampa","123456", "09226329170", false));
        usersById.put(1,new UserModel(1,"mohsen","ghorbani","mohsern","654321", "09398800190", true));
        usersByUsername.put("mohsern", usersById.get(1));
        usersByUsername.put("minampa", usersById.get(0));
        usersByPhoneNumbers.put("09226329170", usersById.get(0));
        usersByPhoneNumbers.put("09398800190", usersById.get(0));
        counter=2;
    }
    public static synchronized UserDb getInstance(){
        if (instance == null){
            instance = new UserDb();
        }
        return instance;
    }
    public UserModel findUserById(int id){
        return usersById.get(id);
    }
    public UserModel findUserByUsername(String username){
        return usersByUsername.get(username);
    }
    public UserModel findUserByPhoneNumber(String phoneNumber){
        return usersByPhoneNumbers.get(phoneNumber);
    }
    public void insertUsers(UserModel user) {
        usersById.put(user.id, user);
        usersByUsername.put(user.username, user);
        usersByPhoneNumbers.put(user.phoneNumber, user);
    }

    public void putPhoneNumbers(String phoneNumber, UserModel user){
        usersByPhoneNumbers.put(phoneNumber, user);
    }
    public void updateUser(UserModel newUser){

    }
    public List<UserModel> getUsersById(){
        return usersById.values().stream().toList();
//        return new LinkedList<>(users.values());
    }







}
