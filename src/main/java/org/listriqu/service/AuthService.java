package org.listriqu.service;

import org.listriqu.dto.LoginRequestDTO;
import org.listriqu.entity.MasterRole;
import org.listriqu.entity.MasterUser;
import org.listriqu.repository.MasterRoleRepository;
import org.listriqu.repository.MasterUserRepository;
import org.listriqu.response.LoginResponse;
import org.listriqu.response.LoginResponse.Data;
import org.listriqu.security.JwtUtils;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AuthService {

    @Inject
    MasterUserRepository masteruserRepository;
    @Inject
    MasterRoleRepository masterroleRepository;

    @Inject
    JwtUtils jwtUtils;

    @Transactional
    public LoginResponse login(LoginRequestDTO request) {
        MasterUser user = masteruserRepository.findByUsername(request.username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Verifikasi password dengan BCrypt
        BCrypt.Result result = BCrypt.verifyer().verify(request.password.toCharArray(), user.getPassword());
        if (!result.verified) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtils.generateToken(user);

        // Build response
        LoginResponse response = new LoginResponse();
        response.status = "success";
        response.message = "Login successful";

        Data responseData = new Data();
        responseData.user_id = user.getUserId();
        responseData.username = user.getUsername();
        responseData.full_name = user.getFullName();
        responseData.role_id = user.getRole().getRoleId();
        responseData.token = token;

        response.data = responseData;
        return response;
    }
   @Transactional
public void createUser(String username, String email, String plainPassword, Integer roleId) {

    MasterUser user = new MasterUser();
    user.setUsername(username);
    user.setEmail(email);
    user.setFullName("John Doe");

    // Hash password menggunakan BCrypt
    String hashedPassword = BCrypt.withDefaults().hashToString(12, plainPassword.toCharArray());
    user.setPassword(hashedPassword);

    // Cari role dari repository
    MasterRole role = masterroleRepository.findById(roleId);
    if (role == null) {
        throw new RuntimeException("Role not found");
    }
    user.setRole(role);

    masteruserRepository.persist(user);
}

}
