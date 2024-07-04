package com.saran.Jwt_Auth.Dtos;

public record AuthResponseDto(
        String Token,
        AuthStatus authStatus
) {
}
