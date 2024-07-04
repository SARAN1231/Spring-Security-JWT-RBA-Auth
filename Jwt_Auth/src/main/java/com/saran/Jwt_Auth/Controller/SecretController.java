package com.saran.Jwt_Auth.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secret")
@CrossOrigin(origins = "http://localhost:3000",maxAge = 3600)
public class SecretController {

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public ResponseEntity<String> secret() {
        return new ResponseEntity<>("Secret Controller USER" , HttpStatus.OK);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> secretadmin() {
        return new ResponseEntity<>("Secret Controller ADMIN" , HttpStatus.OK);
    }
}
