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

@Service
public class UserService{
    @Autowired
    public UserRepository userRepository ;
    @Autowired
    public TokenRepository tokenRepository;
    @Autowired
    public ActivationCodeRepository activationCodeRepository;
    @Autowired
    AuthenticationManager authenticationManager;
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
        user.setActive(false);
        UserModel savedUser = userRepository.save(user);
        sendCodeToUser(savedUser.getId());
        return user;
    }

//    private void sendCodeToUser(int id,String activationCode) {
//        System.out.println("id: "+id + "   ===>  activation code: "+activationCode);
//    }

    public void sendCodeToUser(int id) {
        activationCodeRepository.deleteByUserId(id);
        String activationCode = generateActivationCode();
        activationCodeRepository
                .save(ActivationModel.builder().userId(id).activationCode(activationCode).build());
        System.out.println("id: "+id + "   ===>  activation code: "+activationCode);
    }

    public String generateActivationCode(){
        StringBuilder activationCode = new StringBuilder();
        for(int i=0; i<6; i++){
            Random random = new Random();
            String r = String.valueOf(random.nextInt(0,9));
            activationCode.append(r);
        }
        return activationCode.toString();
    }


    public UserModel updateUser(UserModel newUser){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        UserModel oldUser = userRepository.findUserModelById(newUser.getId());
        UserModel updatedUser = userRepository.save(newUser);
        if (!updatedUser.getUsername().equals(oldUser.getPhoneNumber())){
            sendCodeToUser(newUser.getId());
        }
        return updatedUser;
    }

    @Autowired
    JwtUtils jwtUtils;

    public String login(String username, String password) throws Exception{
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserModel user = userRepository.findUserModelByUsername(username);
        if (user!=null && passwordEncoder.matches(password,user.getPassword()))
            return jwtUtils.generateJwtToken(username);
        else throw new Exception("unauthorized");
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
