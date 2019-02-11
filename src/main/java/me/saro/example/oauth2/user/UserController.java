package me.saro.example.oauth2.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @GetMapping("/abc")
    public String abc() {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        // org.springframework.security.oauth2.provider.OAuth2Authentication@bfce795c: Principal: saro; Credentials: [PROTECTED]; Authenticated: true; Details: remoteAddress=127.0.0.1, tokenType=bearertokenValue=<TOKEN>; Granted Authorities: admin, master
        System.out.println(auth);
        
        // 
        System.out.println(auth.getCredentials());
        
        // remoteAddress=127.0.0.1, tokenType=bearertokenValue=<TOKEN>
        System.out.println(auth.getDetails());
        
        // saro
        System.out.println(auth.getName());
        
        // saro
        System.out.println(auth.getPrincipal());
        
        // [admin, master]
        System.out.println(auth.getAuthorities());
        
        return "HELLO ["+auth.getName()+"]";
    }
}
