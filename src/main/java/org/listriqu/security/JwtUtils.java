package org.listriqu.security;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class JwtUtils {

    @Inject
    JWTParser jwtParser;

    public String generateToken(Integer userId, String username, String roleCode, String roleName) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("sub", userId.toString());
        claims.put("preferred_username", username);
        claims.put("groups", Collections.singletonList(roleCode));
        claims.put("user_id", userId);
        claims.put("role_name", roleName);
        claims.put("jti", UUID.randomUUID().toString());

        Instant now = Instant.now();

        return Jwt.claims(claims)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(60 * 60 * 24)) // expired dalam 1 hari
                .issuer("listriqu-app")
                .sign();
    }

    public boolean validateToken(String token) {
        try {
            jwtParser.parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

  public Map<String, Object> parseTokenClaims(String token) throws Exception {
    DefaultJWTCallerPrincipal jwt = (DefaultJWTCallerPrincipal) jwtParser.parse(token);
    Map<String, Object> claims = new HashMap<>();
    
    claims.put("user_id", jwt.getClaim("user_id"));
    claims.put("username", jwt.getClaim("username"));
    claims.put("role_code", jwt.getClaim("role_code"));
    
    return claims;
}

}
