package com.fitness_management.security;

import com.fitness_management.services.PersonneService;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.web.servlet.filter.OrderedHiddenHttpMethodFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final PersonneService personneService;

    public SecurityConfig(JwtAuthorizationFilter jwtAuthorizationFilter, PersonneService personneService) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.personneService = personneService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/dashboard-admin/**").hasRole("ADMIN") // Accessible uniquement par les Admins
       .requestMatchers("/dashboard-coach/**").hasRole("COACH") // Accessible uniquement par les Coaches
       .requestMatchers("/dashboard-user/**").hasRole("UTILISATEUR") // Accessible uniquement par les Utilisateurs
                                .anyRequest().permitAll()

//
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public OrderedHiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new OrderedHiddenHttpMethodFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @PostConstruct
    public void initAdminUser() {
        personneService.createAdminUserIfNotExist();
    }
}
