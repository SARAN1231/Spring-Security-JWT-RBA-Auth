package com.saran.Jwt_Auth.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity // if u want use preAuthorize anotation then this must be used
public class SecurityFilterConfig {

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityFilterConfig(AuthenticationEntryPoint authenticationEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }



    @Bean // exposing inbuilt filter
    SecurityFilterChain securityFilterChain(HttpSecurity httpsecurity) throws Exception {
        // disable cors
        httpsecurity.cors(AbstractHttpConfigurer::disable);
        //disable  csrf
        httpsecurity.csrf(AbstractHttpConfigurer::disable);
        // Filtering Http Requests
        httpsecurity.authorizeHttpRequests(
                requests -> requests.requestMatchers("/api/auth/signin").permitAll()
                        .requestMatchers("/api/auth/signup").permitAll()
                        .requestMatchers("/api/secret/user").hasAnyAuthority("USER")
                        .requestMatchers("/api/secret/admin").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()); // remaining req should be accessed by authenticated user

        // AuthenticationEntryPoint is Exception handler for Security related  Exceptions
        httpsecurity.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint));

        // Set session policy =stateless (while using JWT no need to store the session)
        httpsecurity.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Adding JWT Authentication Filter
        httpsecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpsecurity.build();
    }
}
