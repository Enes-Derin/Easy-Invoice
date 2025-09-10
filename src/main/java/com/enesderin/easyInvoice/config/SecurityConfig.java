package com.enesderin.easyInvoice.config;

import com.enesderin.easyInvoice.exception.handler.AuthEntryPoint;
import com.enesderin.easyInvoice.model.User;
import com.enesderin.easyInvoice.security.JwtAuthenticaitonFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static String REGISTER = "/register";
    public static String LOGIN = "/login";
    public static String REFRESH_TOKEN = "/refreshToken";

    @Autowired
    private JwtAuthenticaitonFilter jwtAuthenticaitonFilter;
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private AuthEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests(request -> {
            request.requestMatchers(REGISTER,LOGIN,REFRESH_TOKEN).permitAll();
            request.requestMatchers("/api/**").hasAnyRole("ADMIN","MANAGER");
        });
        http.exceptionHandling().authenticationEntryPoint(authEntryPoint).and();
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(jwtAuthenticaitonFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
