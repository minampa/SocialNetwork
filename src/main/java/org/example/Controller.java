package org.example;


import org.example.database.ActivationCodeDb;
import org.example.database.TokenDb;
import org.example.database.UserDb;
import org.example.model.ActivationModel;
import org.example.model.TokenModel;
import org.example.model.UserModel;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.TreeMap;


@RestController
public class Controller {


    @Autowired
    UserService userService;

    @GetMapping(path = "/userById")
    public UserModel findUserByID(@RequestParam int id) {
        return userService.findUserById(id);
    }

    @GetMapping(path = "/userByUsername")
    public UserModel findUserByUsername(@RequestParam String username){
        return userService.findUserByUsername(username);
    }


    @RequestMapping(method = RequestMethod.POST, path = "/register", consumes = "application/json")
    public UserModel registerUser(@RequestBody UserModel user){
        if (user.username == null || user.username.equals("") ||
            user.phoneNumber==null || user.phoneNumber.equals("")) {
                UserModel response = new UserModel();
                response.message = "invalid input";
                return response;
        }

        return userService.registerUser(user);

    }
//    @PostMapping(path = "/postActivationCode", consumes = "application/json")
//    public  UserModel postActivationCode(@RequestBody UserModel user){
//        userData.
//        String activationCode = ;
//    }

    @GetMapping(path = "/users")
    public List<UserModel> getUsers(){
        return userService.getUsers();
    }

    @GetMapping(path = "/activationCode")
    public List<ActivationModel> getActivationCodes(){
        return userService.activationCodeRepository.findAll();
    }

    @Transactional
    @PostMapping(path = "/activate")
    public String activate(@RequestParam int id,@RequestParam String activationCode){
        UserModel user = userService.findUserById(id);
        if (user == null) return "user not found";
        ActivationModel activationCodeInServer = userService.activationCodeRepository.findActivationCodeByUserId(id);
        if (activationCode.equals(activationCodeInServer.activationCode)) {
            user.isActive = true;
            userService.activationCodeRepository.deleteByUserId(id);
            return "activated successfully.";
        }else return "your code is wrong.";
        //we should save the user but user is in memory ram, we don't need to save it.
    }


    @PostMapping(path = "/update")
    public UserModel updateUser(@RequestBody UserModel newUser){
        return userService.updateUser(newUser);
    }

    @PostMapping(path = "/login")
    public String login(@RequestParam String username, @RequestParam String password){
        return userService.login(username, password);
    }

    @DeleteMapping(path = "/logout")
    public void logout(@RequestParam int id, @RequestParam String token){
        userService.logout(id, token);
    }

    @GetMapping(path = "/myInfo")
    public UserModel getMyInfo(Integer id, String token){
        if (id==null || token==null) return null;
        return userService.getMyInfo(id, token);
    }

    @GetMapping(path = "/getTokens")
    public List<TokenModel> getTokens(){
        return userService.tokenRepository.findAll();
    }
}

