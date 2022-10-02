package org.example.database;

import org.example.model.UserModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.TreeMap;

@Component
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
        usersById.put(user.getId(), user);
        usersByUsername.put(user.getUsername(), user);
        usersByPhoneNumbers.put(user.getPhoneNumber(), user);
    }

    public void putPhoneNumbers(String phoneNumber, UserModel user){
        usersByPhoneNumbers.put(phoneNumber, user);
    }
    public UserModel updateUser(UserModel newUser){
        UserModel oldUser = findUserById(newUser.getId());
        if (oldUser == null) return null;
        if (!oldUser.getPhoneNumber().equals(newUser.getPhoneNumber()) ){
            if (findUserByPhoneNumber(newUser.getPhoneNumber()) != null) return null;
            oldUser.setPhoneNumber(newUser.getPhoneNumber());
            newUser.setActive(false);
            putPhoneNumbers(newUser.getPhoneNumber(), newUser);
        }
        if (!oldUser.getUsername().equals(newUser.getUsername())){
            if (findUserByUsername(newUser.getUsername()) != null) return null;
            oldUser.setUsername(newUser.getUsername());
        }
        oldUser.setFirstName(newUser.getFirstName());
        oldUser.setLastName(newUser.getLastName());
        return newUser;
    }
    public List<UserModel> getUsersById(){
        return usersById.values().stream().toList();
//        return new LinkedList<>(users.values());
    }







}
