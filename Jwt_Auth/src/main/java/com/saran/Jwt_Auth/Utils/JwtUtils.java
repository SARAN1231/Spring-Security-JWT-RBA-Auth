package com.saran.Jwt_Auth.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
@Component
@Slf4j // logger
public class JwtUtils {


    private  static final SecretKey secretKey = Jwts.SIG.HS256.key().build(); // generating secret key
    private  static final String  ISSUER = "saran_auth_jwt";

    public static boolean ValidateToken(String jwtToken) {
        return parseToken(jwtToken).isPresent();
    }

    private static Optional<Claims> parseToken(String jwtToken) {
        var jwtparser = Jwts.parser().verifyWith(secretKey).build();

        try {
          return   Optional.of(jwtparser.parseSignedClaims(jwtToken).getPayload()); // if true payload will be sent
        } catch (JwtException | IllegalArgumentException e ) {
            log.error("Jwt Exception occurred");
        }
        return Optional.empty(); // if error occurred  // option is used  to handle the null values ec
    }


    public static Optional<String> getUsernameFromToken(String jwtToken) {
        var claimsOptional  = parseToken(jwtToken);

        if(claimsOptional.isPresent()) {
            return Optional.of(claimsOptional.get().getSubject());// subject  contains user details
        }
        return Optional.empty();

    }

    public String GenerateToken(String username) {
        var currDate = new Date();
        var JwtExpirationInMinutes = 10;
        var ExpirationTime = DateUtils.addMinutes(currDate, JwtExpirationInMinutes);
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .issuer(ISSUER)
                .subject(username)
                .signWith(secretKey)
                .issuedAt(currDate)
                .expiration(ExpirationTime)
                .compact();

    }
}
