package com.example.JWT.Config;

import com.example.JWT.Model.Users;
import com.example.JWT.Repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.List;


@Configuration
@EnableWebSecurity
public class FilterChain {

    @Autowired
    JWTConfiguration jwtfilter ;

    @Autowired
    UsersRepo usersRepo;

    @Bean
    public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("./admin").hasRole("ADMIN")
                        .requestMatchers("/Signup").anonymous()
                        .requestMatchers("/Login").permitAll()
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults()) //since we are not changing anything that's why with default static method can be called
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider( ){
       DaoAuthenticationProvider auth =  new DaoAuthenticationProvider();
       auth.setUserDetailsService(userDetailsService());
       auth.setPasswordEncoder(encoder());
       return auth;
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            Users user = usersRepo.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with email: " + email);
            }
            return user; // Users class should implement UserDetails
        };
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET","POST"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**",configuration);

        return source;
    }

}
