package org.listriqu.security;

import java.time.Duration;

import org.listriqu.entity.MasterUser;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtUtils {

    public String generateToken(MasterUser user) {
        return Jwt.issuer("listriqu-auth")
                .claim("username", user.getUsername())
                .claim("role", user.getRole().getRoleCode())
                .expiresIn(Duration.ofHours(2))
                .sign();
    }
}

