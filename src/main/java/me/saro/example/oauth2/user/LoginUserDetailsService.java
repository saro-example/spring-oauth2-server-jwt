package me.saro.example.oauth2.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class LoginUserDetailsService implements UserDetailsService {
    @Autowired AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String ac) throws UsernameNotFoundException {        
        return new UserDetails(accountRepository.findByAccount(ac).orElseThrow(() -> new UsernameNotFoundException("Bad credentials")));
    }
}