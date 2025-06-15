package org.listriqu.resource;

import org.listriqu.dto.LoginRequestDTO;
import org.listriqu.dto.UserRegisterDTO;
import org.listriqu.response.LoginResponse;
import org.listriqu.service.AuthService;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;
@Context
HttpHeaders headers;
    @POST
    @Path("/login")
      @PermitAll 
    public LoginResponse login(LoginRequestDTO loginRequest, @Context HttpHeaders headers) {
        String userAgent = headers.getHeaderString("User-Agent");
        String ipAddress = headers.getHeaderString("X-Forwarded-For"); // fallback IP
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = "127.0.0.1"; // Default localhost (jika running local)
        }

        return authService.login(loginRequest, ipAddress, userAgent);
    }

@POST
@Path("/logout")
@RolesAllowed({"USER", "ADMIN"})
public void logout(@Context HttpHeaders headers) {
    String authHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        throw new WebApplicationException("Missing token", Response.Status.UNAUTHORIZED);
    }
    String token = authHeader.replace("Bearer ", "");
    authService.logout(token);
}

    @POST
    @Path("/register")
      @PermitAll 
    public Response register(UserRegisterDTO request) {
        authService.createUser(request.username, request.email, request.password, request.roleId);
        return Response.ok("User created successfully").build();
    }
}
