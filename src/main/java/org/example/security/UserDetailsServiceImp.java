package org.example.security;


import org.example.UserService;
import org.example.model.UserModel;
import org.example.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    //we can add autowired before userrepository and remove the constructor
    private final UserRepository userRepository;

    public UserDetailsServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findUserModelByUsername(username);
        if (user!=null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(), user.getPassword(), user.isActive(),

                    true, true, true,
                    getAuthorities(List.of("User")));
        }
        throw  new UsernameNotFoundException("user not found!");
    }


    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<String> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }

}
