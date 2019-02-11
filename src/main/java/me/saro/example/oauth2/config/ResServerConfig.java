package me.saro.example.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import lombok.SneakyThrows;
import me.saro.commons.Converter;

@Configuration
@EnableResourceServer
public class ResServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
         resources.tokenServices(tokenServices()).resourceId("res-saro").stateless(false);
    }
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.anonymous().disable()
        .authorizeRequests()
        .antMatchers("/user/**").authenticated()
        .and()
        .exceptionHandling()
        .accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
    
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
    
    @Bean("resToken")
    @SneakyThrows
    public JwtAccessTokenConverter accessTokenConverter() {
        // keytool -genkeypair -alias mytest  -keyalg RSA  -keypass 123456  -keystore mytest.jks  -storepass 123456
        // keytool -list -rfc --keystore mytest.jks | openssl x509 -inform pem -pubkey
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        
        String publicKey = Converter.toString(new ClassPathResource("mytest.cer").getInputStream(), "UTF-8");
        
        System.out.println(publicKey);
        converter.setVerifierKey(publicKey);
         
        return converter;
    }
 
    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }
}
