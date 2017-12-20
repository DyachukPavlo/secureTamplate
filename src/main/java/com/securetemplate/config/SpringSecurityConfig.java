package com.securetemplate.config;

import com.securetemplate.services.AccessDeniedHandler;
import com.securetemplate.services.SecurityUserDetailsService;
import com.securetemplate.services.TokenAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Order(2)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthProvider authenticationProvider;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private TokenAuthService tokenAuthService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
            .addFilterBefore(new StatelessAuthenticationFilter(tokenAuthService), UsernamePasswordAuthenticationFilter.class)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests().antMatchers("/login").permitAll().and()
            .authorizeRequests().antMatchers("/checkToken").permitAll().and()
            .authorizeRequests().antMatchers("/register").permitAll().and()
            .authorizeRequests().antMatchers("**/**").fullyAuthenticated().and()
            .authenticationProvider(authenticationProvider)
            .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }

    @Bean
    public Md5PasswordEncoder md5PasswordEncoder() {
        return new Md5PasswordEncoder();
    }

    @Value("${cors.is-credentials-allowed}")
    private boolean isCredentialsAllowed;

    @Value("${cors.allowed-origin}")
    private String[] allowedOrigin;

    @Value("${cors.allowed-headers}")
    private String[] allowedHeaders;

    @Value("${cors.allowed-methods}")
    private String[] alloweMethods;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(isCredentialsAllowed);
        for (String allowedHeader : allowedOrigin) {
            config.addAllowedOrigin(allowedHeader);
        }
        for (String allowedHeader : allowedHeaders) {
            config.addAllowedHeader(allowedHeader);
        }
        for (String alloweMethod : alloweMethods) {
            config.addAllowedMethod(alloweMethod);
        }
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}