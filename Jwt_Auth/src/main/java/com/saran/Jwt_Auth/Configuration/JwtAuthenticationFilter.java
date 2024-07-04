package com.saran.Jwt_Auth.Configuration;

import com.saran.Jwt_Auth.Utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService; // constructor injection -> RequiredArgsConstructor annotation is used

    // validate the JWT Token
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // get the token from header
        var jwtTokenOptional  = getTokenFromRequest(request);
        //Validate the JWY Token -> Jwt Utils
        jwtTokenOptional.ifPresent(jwtToken -> {
            if(JwtUtils.ValidateToken(jwtToken)) {
                // get the username from token
                var usernameoptional = JwtUtils.getUsernameFromToken(jwtToken);

                usernameoptional.ifPresent(username -> {
                    // fetch the user details using username
                    var userDetails = userDetailsService.loadUserByUsername(username);

                    // create a Authentication Token
                    var authenticationToken =  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetails(request)); // setting some details to the token from request (ipaddress etc..)

                    // set authentication token to Security Context
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken); // this show the active authentication user so we need to store in this
                });


            }
        });
        // pass to next Inbuilt Filters
        filterChain.doFilter(request, response);

    }

    public Optional<String> getTokenFromRequest(HttpServletRequest request) {
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        // remove Bearer prefix token format -> Bearer <Token>
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return Optional.of(authHeader.substring(7));
        }
        return Optional.empty(); // optional is used becoz it is ec to handle null values
    }
}
