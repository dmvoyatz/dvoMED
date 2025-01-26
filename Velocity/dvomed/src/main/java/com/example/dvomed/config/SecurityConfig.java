package com.example.dvomed.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import com.example.dvomed.filters.JwtAuthenticationFilter;
import com.example.dvomed.services.UserServiceImpl;
import com.example.dvomed.utils.JwtUtil;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private JwtUtil jwtUtil;

    // Inject the custom UserDetailsService (UserServiceImpl)
    public SecurityConfig(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new JwtAuthenticationFilter((UserServiceImpl)userDetailsService, jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.POST, "/api/auth/register", "/api/auth/login").permitAll() // Allow registration endpoint without authentication
                                .requestMatchers(HttpMethod.GET,"/api/dashboard/**").permitAll() // Allow public dashboard endpoint without authentication
                                .requestMatchers(HttpMethod.GET,"/api/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated() // Secure all other endpoints //.denyAll();
                )
                .csrf(csrf -> csrf.disable()); // Disable CSRF protection if needed for testing (use cautiously)
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                   .userDetailsService(userDetailsService) // Use the custom UserDetailsService
                   .passwordEncoder(passwordEncoder())
                   .and()
                   .build();
    }

    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
