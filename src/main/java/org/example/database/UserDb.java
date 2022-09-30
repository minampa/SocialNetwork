package org.example.database;

import org.example.model.UserModel;

import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class UserDb {
    private static UserDb instance = null;
    private ActivationCodeDb activationCodeDb;
    private TokenDb tokenDb;
    int counter = 0 ;
    private TreeMap<Integer, UserModel> users = new TreeMap<>();
    private TreeMap<String,UserModel> usernames = new TreeMap<>();
    private TreeMap<String,UserModel> phoneNumbers = new TreeMap<>();
    private UserDb(){
        activationCodeDb = ActivationCodeDb.getInstance();
        users.put(0,new UserModel(0,"mina","ghorbani","minampa","123456", "09226329170", false));
        users.put(1,new UserModel(1,"mohsen","ghorbani","mohsern","654321", "09398800190", true));
        usernames.put("mohsern",users.get(1));
        usernames.put("minampa",users.get(0));
        phoneNumbers.put("09226329170",users.get(0));
        phoneNumbers.put("09398800190",users.get(0));
        counter=2;
    }
    public static synchronized UserDb getInstance(){
        if (instance == null){
            instance = new UserDb();
        }
        return instance;
    }
    public UserModel findUserById(int id){
        return users.get(id);
    }
    public UserModel findUserByPhoneNumber(String phoneNumber){
        return users.get(phoneNumber);
    }
    public UserModel findUserByUsername(String username){
        return users.get(username);
    }
    public List<UserModel> getUsers(){
        return users.values().stream().toList();
//        return new LinkedList<>(users.values());
    }
    public UserModel registerUser(UserModel user){
        if (usernames.get(user.username)!=null)
            return null;
        if (phoneNumbers.get(user.phoneNumber)!=null)
            return null;
        String activationCode = generateActivationCode();
        user.isActive = false;
        user.id = counter;
        activationCodeDb.activationCode.put(user.id, activationCode);
        sendCodeToUser(user.id,activationCode);
        //todo check, username,
        users.put(counter,user);
        usernames.put(user.username, user);
        phoneNumbers.put(user.phoneNumber, user);
        counter++;
        return user;
    }

    private void sendCodeToUser(int id,String activationCode) {
        System.out.println("id: "+id + "   ===>  activation code: "+activationCode);
    }

    public String generateActivationCode(){
        String activationCode = "";
        for(int i=0; i<6; i++){
            Random random = new Random();
            String r = String.valueOf(random.nextInt(0,9));
            activationCode += r;
        }
        return activationCode;
    }

    public UserModel updateUser(UserModel newUser){
        UserModel oldUser = findUserById(newUser.id);
        if (oldUser == null) return null;
        if (!oldUser.phoneNumber.equals(newUser.phoneNumber) ){
            if (phoneNumbers.get(newUser.phoneNumber)!= null) return null;
            oldUser.phoneNumber = newUser.phoneNumber;
            newUser.isActive = false;
            String activationCode = generateActivationCode();
            activationCodeDb.activationCode.remove(newUser.id, activationCode);
            activationCodeDb.activationCode.put(newUser.id, activationCode);
            sendCodeToUser(newUser.id, activationCode);
            phoneNumbers.put(newUser.phoneNumber, newUser);
        }
        if (!oldUser.username.equals(newUser.username)){
            if (usernames.get(newUser.username) != null) return null;
            oldUser.username = newUser.username;
        }
        oldUser.firstName = newUser.firstName;
        oldUser.lastName = newUser.lastName;
        return newUser;
    }

    public String login(String username, String password){
        UserModel user = findUserByUsername(username);
        if (user != null && password.equals(user.password)){
            String token = createToken();
            tokenDb.tokens.put(user.id, token);
            return token;
        }
        return "";
    }

    public String createToken(){
        Random random = new Random();
        StringBuilder token = new StringBuilder();
        for (int i=0;i<10;i++){
            int r = random.nextInt(0,62);
            if (0<=r && r <=25){
                char s = (char) ((int)'a' + r);
                token.append(s);
            }
            if(26<=r && r<=51){
                char s = (char) ((int) 'A' + r - 26);
                token.append(s);
            }
            if(r>=52){
                char s = (char) ((int) '0' + r - 52);
                token.append(s);
            }
        }
        return token.toString();
    }

    public void logout(int id, String token){
        String t = tokenDb.tokens.get(id);
        if( t.equals(token))
            tokenDb.tokens.remove(id);
    }

    public UserModel getMyInfo(int id, String token) {
        String t = tokenDb.tokens.get(id);
        if (t.equals(token))
            return users.get(id);
        return null;
    }
}
