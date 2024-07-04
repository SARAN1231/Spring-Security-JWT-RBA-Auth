package com.saran.Jwt_Auth.Controller;

import com.saran.Jwt_Auth.Dtos.AuthRequestDto;
import com.saran.Jwt_Auth.Dtos.AuthResponseDto;
import com.saran.Jwt_Auth.Dtos.AuthStatus;
import com.saran.Jwt_Auth.Services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("signin")
    public ResponseEntity<AuthResponseDto> signin(@RequestBody AuthRequestDto authRequestDto) {
        var jwtToken = authService.SignIn(authRequestDto.Username(),authRequestDto.Password());

        var authResponse = new AuthResponseDto(jwtToken, AuthStatus.LOGIN_SUCCESSFULLY);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
    @PostMapping("signup")
    public ResponseEntity<AuthResponseDto> signup(@RequestBody AuthRequestDto authRequestDto) {
        try {
            var jwtToken = authService.SignUp(authRequestDto.name(), authRequestDto.Username(), authRequestDto.Password(), authRequestDto.roles());
            var authResponse = new AuthResponseDto(jwtToken, AuthStatus.USER_CREATE_SUCCESSFULLY);
            return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            var authResponse = new AuthResponseDto(null, AuthStatus.USER_CREATE_FAILED);
            return new ResponseEntity<>(authResponse, HttpStatus.CONFLICT);
        }
    }
}
