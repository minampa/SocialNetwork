package org.example;

import org.example.database.ActivationCodeDb;
import org.example.database.TokenDb;
import org.example.database.UserDb;
import org.example.model.UserModel;

import java.util.List;
import java.util.Random;

public class UserService{

    int counter = 2;
    public UserDb userDb = null;
    public TokenDb tokenDb;
    public UserService(){
        userDb = UserDb.getInstance();
        activationCodeDb = ActivationCodeDb.getInstance();
        tokenDb = TokenDb.getInstance();
    }
    public ActivationCodeDb activationCodeDb;
    public UserModel findUserById(int id){
        return userDb.findUserById(id);
    }
    public UserModel findUserByUsername(String username){
        return userDb.findUserByUsername(username);
    }
    public UserModel findUserByPhoneNumber(String phoneNumber){
        return userDb.findUserByPhoneNumber(phoneNumber);
    }
    public List<UserModel> getUsers(){
        return userDb.getUsersById();
    }
    public UserModel registerUser(UserModel user){
        if (userDb.findUserByUsername(user.username) != null)
            return null;
        if (userDb.findUserByPhoneNumber(user.phoneNumber) != null)
            return null;
//        if (usernames.get(user.username)!=null)
//            return null;
//        if (phoneNumbers.get(user.phoneNumber)!=null)
//            return null;
        String activationCode = generateActivationCode();
        user.isActive = false;
        user.id = counter;
        activationCodeDb.activationCode.put(user.id, activationCode);
        sendCodeToUser(user.id,activationCode);
        //todo check, username,
        userDb.insertUsers(user);
//        users.put(counter,user);
//        usernames.put(user.username, user);
//        phoneNumbers.put(user.phoneNumber, user);
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

    public UserModel updateUser(UserModel newUser){
        UserModel oldUser = userDb.findUserById(newUser.id);
        UserModel updatedUser = userDb.updateUser(newUser);
        if (!updatedUser.phoneNumber.equals(oldUser.phoneNumber)){
            String activationCode = generateActivationCode();
            activationCodeDb.activationCode.remove(newUser.id, activationCode);
            activationCodeDb.activationCode.put(newUser.id, activationCode);
            sendCodeToUser(newUser.id, activationCode);
        }
        return updatedUser;
    }

    public String login(String username, String password){
        UserModel user = findUserByUsername(username);
        if (user != null && user.isActive && password.equals(user.password)){
            String token = createToken();
            tokenDb.tokens.put(user.id, token);
            return token;
        }
        return "";
    }
    public void logout(int id, String token){
        String t = tokenDb.tokens.get(id);
        if( t.equals(token))
            tokenDb.tokens.remove(id);
    }

    public UserModel getMyInfo(int id, String token) {
        String t = tokenDb.tokens.get(id);
        if (t!=null && t.equals(token))
            return userDb.findUserById(id);
        return null;
    }


}
