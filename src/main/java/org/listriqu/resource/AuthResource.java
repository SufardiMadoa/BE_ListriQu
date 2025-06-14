package org.listriqu.resource;

import org.listriqu.dto.LoginRequestDTO;
import org.listriqu.dto.UserRegisterDTO;
import org.listriqu.response.LoginResponse;
import org.listriqu.service.AuthService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    public LoginResponse login(LoginRequestDTO request) {
        return authService.login(request);
    }
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(UserRegisterDTO request) {
       authService.createUser(request.username, request.email, request.password, request.roleId);
        return Response.ok("User created successfully").build();
    }
}
