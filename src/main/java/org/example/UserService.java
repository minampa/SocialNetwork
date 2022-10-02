package org.example;


import org.example.model.ActivationModel;
import org.example.model.TokenModel;
import org.example.model.UserModel;
import org.example.repository.ActivationCodeRepository;
import org.example.repository.TokenRepository;
import org.example.repository.UserRepository;
import org.example.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Component
public class UserService{
    int counter = 2;
    @Autowired
    public UserRepository userRepository ;
    @Autowired
    public TokenRepository tokenRepository;
    @Autowired
    public ActivationCodeRepository activationCodeRepository;
    public UserModel findUserById(int id){
        return userRepository.findUserModelById(id);
    }
    public UserModel findUserByUsername(String username){
        return userRepository.findUserModelByUsername(username);
    }
    public UserModel findUserByPhoneNumber(String phoneNumber){
        return userRepository.findUserModelByPhoneNumber(phoneNumber);
    }
    public List<UserModel> getUsers(){
        return userRepository.findAll();
    }
    @Autowired
    PasswordEncoder passwordEncoder;
    public UserModel registerUser(UserModel user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRepository.findUserModelByUsername(user.getUsername()) != null)
            return null;
        if (userRepository.findUserModelByPhoneNumber(user.getPhoneNumber()) != null)
            return null;
//        if (usernames.get(user.username)!=null)
//            return null;
//        if (phoneNumbers.get(user.phoneNumber)!=null)
//            return null;
        String activationCode = generateActivationCode();
        user.setActive(false);
        user.setId(counter);
        activationCodeRepository.save(new ActivationModel(user.getId(),activationCode));
//        activationCodeDb.activationCodes.put(user.id, activationCode);
        sendCodeToUser(user.getId(),activationCode);
        //todo check, username,
        userRepository.save(user);
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


//    public String createToken(){
//        Random random = new Random();
//        StringBuilder token = new StringBuilder();
//        for (int i=0;i<10;i++){
//            int r = random.nextInt(0,62);
//            if (0<=r && r <=25){
//                char s = (char) ((int)'a' + r);
//                token.append(s);
//            }
//            if(26<=r && r<=51){
//                char s = (char) ((int) 'A' + r - 26);
//                token.append(s);
//            }
//            if(r>=52){
//                char s = (char) ((int) '0' + r - 52);
//                token.append(s);
//            }
//        }
//        return token.toString();
//    }

    public UserModel updateUser(UserModel newUser){
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        UserModel oldUser = userRepository.findUserModelById(newUser.getId());
        UserModel updatedUser = userRepository.save(newUser);
        if (!updatedUser.getUsername().equals(oldUser.getPhoneNumber())){
            String activationCode = generateActivationCode();
            activationCodeRepository.deleteByUserId(newUser.getId());
            activationCodeRepository.save(new ActivationModel(newUser.getId(), activationCode));
            sendCodeToUser(newUser.getId(), activationCode);
        }
        return updatedUser;
    }

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AuthenticationManager authenticationManager;
    public String login(String username, String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        return jwtUtils.generateJwtToken(authentication);
    }
    @Transactional
    public void logout(int id, String token){
        TokenModel t = tokenRepository.findByUserId(id);
        if( t.getToken().equals(token))
            tokenRepository.deleteByUserId(id);
    }

    public UserModel getMyInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findUserModelByUsername(username);
    }


    public List<ActivationModel> findAllActivationCodes() {
        return activationCodeRepository.findAll();
    }

    public List<TokenModel> findAllTokens() {
        return tokenRepository.findAll();
    }

    public void deleteActivationCodeByUserId(int id) {
        activationCodeRepository.deleteByUserId(id);
    }

    public ActivationModel findActivationCodeByUserId(int id) {
        return activationCodeRepository.findActivationCodeByUserId(id);
    }
}
