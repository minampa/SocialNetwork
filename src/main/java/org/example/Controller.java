package org.example;


import org.example.database.ActivationCodeDb;
import org.example.database.UserDb;
import org.example.model.UserModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.TreeMap;


@RestController
public class Controller {

    UserDb userDb;
    ActivationCodeDb activationCodeDb;

    public Controller(){
        userDb = UserDb.getInstance();
        activationCodeDb = ActivationCodeDb.getInstance();

    }

    @GetMapping(path = "/userById")
    public UserModel findUserByID(@RequestParam int id) {
        return userDb.findUserById(id);
    }

    @GetMapping(path = "/userByUsername")
    public UserModel findUserByUsername(@RequestParam String username){
        return userDb.findUserByUsername(username);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/register", consumes = "application/json")
    public UserModel registerUser(@RequestBody UserModel user){
        if (user.username == null || user.username.equals("") ||
            user.phoneNumber==null || user.phoneNumber.equals("")) {
                UserModel response = new UserModel();
                response.message = "invalid input";
                return response;
        }

        return userDb.registerUser(user);

    }
//    @PostMapping(path = "/postActivationCode", consumes = "application/json")
//    public  UserModel postActivationCode(@RequestBody UserModel user){
//        userData.
//        String activationCode = ;
//    }

    @GetMapping(path = "/users")
    public List<UserModel> getUsers(){
        return userDb.getUsers();
    }

    @GetMapping(path = "/activationCode")
    public TreeMap<Integer,String> getActivationCodes(){
        return activationCodeDb.activationCode;
    }

    @PostMapping(path = "/activate")
    public String activate(@RequestParam int id,@RequestParam String activationCode){
        UserModel user = userDb.findUserById(id);
        if (user == null) return "user not found";
        String activationCodeInServer = activationCodeDb.activationCode.get(id);
        if (activationCode.equals(activationCodeInServer)) {
            user.isActive = true;
            activationCodeDb.activationCode.remove(id);
            return "activated successfully.";
        }else return "your code is wrong.";
        //we should save the user but user is in memory ram, we don't need to save it.
    }


    @PostMapping(path = "/update")
    public UserModel updateUser(@RequestBody UserModel newUser){
        return userDb.updateUser(newUser);
    }

    @PostMapping(path = "/login")
    public String login(@RequestParam String username, @RequestParam String password){
        return userDb.login(username, password);
    }

    @DeleteMapping(path = "/logout")
    public void logout(@RequestParam int id, @RequestParam String token){
        userDb.logout(id, token);
    }

    @GetMapping(path = "/myInfo")
    public UserModel getMyInfo(int id, String token){
        return userDb.getMyInfo(id, token);
    }

//    @PutMapping(path = "/update")

}

