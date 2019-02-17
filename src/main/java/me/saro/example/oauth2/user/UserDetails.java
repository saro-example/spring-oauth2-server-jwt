package me.saro.example.oauth2.user;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class UserDetails extends User {
    private static final long serialVersionUID = 1L;
    
    public UserDetails(Account account) {
        super(
                account.getAccount(), 
                account.getPassword(), 
                account.getRoles().stream()
                    .map(e -> new SimpleGrantedAuthority(e.getRole())).collect(Collectors.toList())
        );
    }
}