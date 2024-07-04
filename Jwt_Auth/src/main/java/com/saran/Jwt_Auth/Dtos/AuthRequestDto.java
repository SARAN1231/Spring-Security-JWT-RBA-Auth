package com.saran.Jwt_Auth.Dtos;

import java.util.List;

public record AuthRequestDto(
        String name,String Username ,String Password ,  List<String> roles
) {
}
