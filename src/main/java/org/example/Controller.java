package org.example;



import org.example.model.ActivationModel;
import org.example.model.TokenModel;
import org.example.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.List;


@RestController
public class Controller {


    @Autowired
    public UserService userService;

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
        if (user.getUsername() == null || user.getUsername().equals("") ||
            user.getPhoneNumber()==null || user.getPhoneNumber().equals("")) {
                UserModel response = new UserModel();
                return response;
        }

        return userService.registerUser(user);

    }
    @PutMapping(path = "/resendCode")
    public void resend(int id){
        userService.sendCodeToUser(id);
    }

    @GetMapping(path = "/users")
    public List<UserModel> getUsers(){
        return userService.getUsers();
    }

    @GetMapping(path = "/activationCode")
    public List<ActivationModel> getActivationCodes(){
        return userService.findAllActivationCodes();
    }

    @Transactional
    @PostMapping(path = "/activate")
    public String activate(@RequestParam int id,@RequestParam String activationCode){
        UserModel user = userService.findUserById(id);
        if (user == null) return "user not found";
        ActivationModel activationCodeInServer = userService.findActivationCodeByUserId(id);
        if (activationCode.equals(activationCodeInServer.getActivationCode())) {
            user.setActive(true);
            userService.deleteActivationCodeByUserId(id);
            return "activated successfully.";
        }else return "your code is wrong.";
        //we should save the user but user is in memory ram, we don't need to save it.
    }


    @PostMapping(path = "/update")
    public UserModel updateUser(@RequestBody UserModel newUser){
        return userService.updateUser(newUser);
    }

    @PostMapping(path = "/login")
    public String login(@RequestParam String username, @RequestParam String password) throws Exception {
        return userService.login(username, password);
    }

    @DeleteMapping(path = "/logout")
    public void logout(@RequestParam int id, @RequestParam String token){
        userService.logout(id, token);
    }

    @GetMapping(path = "/myInfo")
    public UserModel getMyInfo(){
        return userService.getMyInfo();
    }

    @GetMapping(path = "/getTokens")
    public List<TokenModel> getTokens(){
        return userService.findAllTokens();
    }
}

