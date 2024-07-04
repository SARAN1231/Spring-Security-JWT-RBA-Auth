package com.saran.Jwt_Auth.Services;

import com.saran.Jwt_Auth.Repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailservice implements UserDetailsService {
    private final UserRepository userRepository;
    public UserDetailservice(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      var newUser = userRepository.findByUsername(username)
              .orElseThrow(() -> new UsernameNotFoundException("UserName Not FoundException"+username));
        return new User(username, newUser.getPassword(), newUser.getRoles());
    }
}
