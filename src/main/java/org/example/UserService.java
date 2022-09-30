package org.example;

import org.example.database.UserDb;

import java.util.Random;

public class UserService{

    public UserDb userDb = null;

    public UserService(){
        userDb = UserDb.getInstance();
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


}
