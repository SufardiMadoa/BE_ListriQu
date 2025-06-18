package org.listriqu.resource;

import org.listriqu.dto.LoginRequestDTO;
import org.listriqu.dto.UserRegisterDTO;
import org.listriqu.response.ErrorResponse;
import org.listriqu.response.SuccesResponse;
import org.listriqu.service.AuthService;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    @PermitAll
    public Response login(LoginRequestDTO loginRequest, @Context HttpHeaders headers) {
        String userAgent = headers.getHeaderString("User-Agent");
        String ipAddress = headers.getHeaderString("X-Forwarded-For"); // fallback IP
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = "127.0.0.1"; // Default localhost (jika running local)
        }
        return authService.login(loginRequest, ipAddress, userAgent);
    }

    @POST
    @Path("/logout")
    @RolesAllowed({ "SUPERADMIN", "USER", "ADMIN" })
    public Response logout(@Context HttpHeaders headers) {
        String authHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(401)
                    .entity(new ErrorResponse("Unauthorized"))
                    .build();
        }
        String token = authHeader.replace("Bearer ", "");
        authService.logout(token);
        return Response.ok(new SuccesResponse("Success", "Logout successful")).build();
    }

    @POST
    @Path("/register")
    @PermitAll
    public Response register(UserRegisterDTO request) {
        authService.createUser(request.username, request.email, request.password, request.roleId);
        return Response.ok(new SuccesResponse("Success", "User created successfully")).build();
    }
}
