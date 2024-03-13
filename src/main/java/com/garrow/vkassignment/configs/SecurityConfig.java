package com.garrow.vkassignment.configs;

import com.garrow.vkassignment.repositories.jpa.UsersDetailsRepository;
import com.garrow.vkassignment.security.UserDetailsServiceImpl;
import com.garrow.vkassignment.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final UsersDetailsRepository usersDetailsRepository;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http)
            throws Exception {
        return http.csrf().disable()
                   .cors(Customizer.withDefaults())
                   .authorizeHttpRequests()
                   .requestMatchers("/api/docs/**", "/api/sign-up/**").permitAll()
                   .anyRequest().authenticated().and().httpBasic().and().build();
                   //.and().formLogin(Customizer.withDefaults()).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(new UserDetailsServiceImpl(usersDetailsRepository));
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
