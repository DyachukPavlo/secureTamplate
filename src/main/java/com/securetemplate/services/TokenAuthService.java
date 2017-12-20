package com.securetemplate.services;

import com.securetemplate.config.SimpleResponse;
import com.securetemplate.config.UserAuthentication;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TokenAuthService {

    private static final String AUTH_HEADER_NAME = "x-auth-token";

    private final static int EXPIRATION_TIME = 10;

    private final AuthenticationManager authenticationManager;

    private final TokenHandler tokenHandler;

    private final SecurityUserDetailsService userService;

    public TokenAuthService(AuthenticationManager authenticationManager, TokenHandler tokenHandler, SecurityUserDetailsService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenHandler = tokenHandler;
        this.userService = userService;
    }

    private String createToken(String userName) {
        return tokenHandler.generateAccessToken(userName, LocalDateTime.now().plusMinutes(EXPIRATION_TIME));
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AUTH_HEADER_NAME);
        if(token != null && !token.isEmpty()){
            try {
                return new UserAuthentication(userService.loadUserByUsername(tokenHandler.extractUserName(token)));
            } catch (ExpiredJwtException e) {
                return null;
            }
        }
        return null;
    }

    public SimpleResponse addAuthenticatin(UserDetails userDetails, SimpleResponse concreteResponse) {
        SimpleResponse response = concreteResponse;

        UserDetails foundUser = userService.loadUserByUsername(userDetails.getUsername());
        if (foundUser == null) {
            return (SimpleResponse) response.error("user does not exist").finish();
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(foundUser, userDetails.getPassword(), grantedAuthorities);

        Authentication authenticatedUser = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        response.setPayload(foundUser);
        response.setSecurityToken(createToken(foundUser.getUsername()));

        return response;
    }

    public UserDetails getPrincipal(){
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
