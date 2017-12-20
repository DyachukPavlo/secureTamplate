package com.securetemplate.config;

import com.securetemplate.services.SecurityUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Order(1)
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    public Md5PasswordEncoder md5PasswordEncoder;

    @Autowired
    SecurityUserDetailsService userDetailsService;

    private final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Authentication auth = null;
        if(authentication != null) {
            try {
                auth = authenticationProvider.authenticate(authentication);
                System.out.println("not logged in");
            } catch (Exception ex) {
                System.out.println("authentication exception !");
                ex.printStackTrace();
            }
            if (auth != null) {
                System.out.println("user has logged in :" + auth.getName());
            }
        }
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authenticationProvider.supports(authentication);
    }


    @PostConstruct
    private void init() {
         authenticationProvider.setPasswordEncoder(md5PasswordEncoder);
         authenticationProvider.setUserDetailsService(userDetailsService);
    }
}
