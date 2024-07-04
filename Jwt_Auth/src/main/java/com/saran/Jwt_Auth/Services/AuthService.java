package com.saran.Jwt_Auth.Services;

import com.saran.Jwt_Auth.Models.Role;
import com.saran.Jwt_Auth.Models.UserModel;
import com.saran.Jwt_Auth.Repository.RoleRepository;
import com.saran.Jwt_Auth.Repository.UserRepository;
import com.saran.Jwt_Auth.Utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor // if this annotation is used then no need constructor injection
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    private final JwtUtils jwtUtils;



    public String SignIn(String username, String password) {
        var authtoken = new UsernamePasswordAuthenticationToken(username, password);
       var authenticate = authenticationManager.authenticate(authtoken);  // authentication Token contains principal + Authorities

        UserDetails principal = (UserDetails) (authenticate.getPrincipal());
        return jwtUtils.GenerateToken(principal.getUsername());

    }

    public String SignUp(String name, String username, String password,List<String> roleNames) throws Exception {
        // check whether the user already exists
        if (userRepository.existsByUsername(username)) {
            throw new Exception("User Already Exists");
        }

        // encode the password
        String encodedPassword = passwordEncoder.encode(password);

        UserModel user = new UserModel();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(encodedPassword);

        List<Role> roles = roleNames.stream()
                .map(roleRepository::findByAuthority)
                .toList();

        user.setRoles(roles);

        userRepository.save(user);

        return jwtUtils.GenerateToken(username);
    }
}

