package org.listriqu.security;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.listriqu.response.ErrorResponse;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.auth.principal.JWTParser;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtAuthenticationFilter implements ContainerRequestFilter {

    @Inject
    JWTParser jwtParser;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();

        // Allow list (public endpoints)
        if (path.equals("/api/v1/auth/login") || path.equals("/api/v1/auth/register")) {
            return;
        }

        // Check Authorization header
        String authHeader = requestContext.getHeaderString("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            abort(requestContext, "Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);
        try {
            JsonWebToken jwt = jwtParser.parse(token);

            // Validate expiration (optional)
            if (jwt.getExpirationTime() < (System.currentTimeMillis() / 1000)) {
                abort(requestContext, "Token expired");
            }

            // (Optional) kamu bisa inject user info ke context jika perlu
            requestContext.setProperty("user_id", jwt.getClaim("user_id"));
            requestContext.setProperty("username", jwt.getClaim("username"));
            requestContext.setProperty("role_code", jwt.getClaim("role_code"));

        } catch (ParseException e) {
            abort(requestContext, "Invalid token");
        }
    }

    private void abort(ContainerRequestContext requestContext, String message) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity(new ErrorResponse(message))
                .build());
    }
}
