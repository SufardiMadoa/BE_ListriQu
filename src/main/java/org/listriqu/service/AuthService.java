package org.listriqu.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.listriqu.dto.LoginRequestDTO;
import org.listriqu.dto.MenuDTO;
import org.listriqu.entity.MasterRole;
import org.listriqu.entity.MasterUser;
import org.listriqu.entity.UserSession;
import org.listriqu.enums.SessionStatusEnum;
import org.listriqu.repository.MasterRoleRepository;
import org.listriqu.repository.MasterUserRepository;
import org.listriqu.repository.UserSessionRepository;
import org.listriqu.response.LoginResponse;
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
    UserSessionRepository userSessionRepository;

    @Inject
    RoleMenuService roleMenuService;

    @Inject
    JwtUtils jwtUtils;

   @Transactional
public LoginResponse login(LoginRequestDTO request, String ipAddress, String userAgent) {
    MasterUser user = masteruserRepository.findByUsername(request.username);

    if (user == null) {
        throw new RuntimeException("User not found");
    }

    BCrypt.Result result = BCrypt.verifyer().verify(request.password.toCharArray(), user.getPassword());
    if (!result.verified) {
        throw new RuntimeException("Invalid credentials");
    }

    // Generate token
    String token = jwtUtils.generateToken(
    user.getUserId(),
    user.getUsername(),
    user.getRole().getRoleCode(),
    user.getRole().getRoleName()
);


    // Hash token
    String tokenHash = DigestUtils.sha256Hex(token);

    // Simpan session
    UserSession session = new UserSession();
    session.setUser(user);
    session.setSessionToken(tokenHash);
    session.setIpAddress(ipAddress);
    session.setUserAgent(userAgent);
    session.setLoginAt(LocalDateTime.now());
    session.setStatus(SessionStatusEnum.Active);
    userSessionRepository.persist(session);

    // Build response
    LoginResponse response = new LoginResponse();
    response.status = "success";
    response.message = "Login successful";
    
    List<MenuDTO> menus = roleMenuService.getMenusByRole(user.getRole().getRoleId());

    LoginResponse.Data responseData = new LoginResponse.Data();
    responseData.user_id = user.getUserId();
    responseData.username = user.getUsername();
    responseData.full_name = user.getFullName();
    responseData.role_id = user.getRole().getRoleId();
    responseData.token = token;
    responseData.menus = menus;

    response.data = responseData;

    return response;
}

    @Transactional
    public void createUser(String username, String email, String plainPassword, Integer roleId) {

        MasterUser user = new MasterUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setFullName("John Doe");

        String hashedPassword = BCrypt.withDefaults().hashToString(12, plainPassword.toCharArray());
        user.setPassword(hashedPassword);

        MasterRole role = masterroleRepository.findById(roleId);
        if (role == null) {
            throw new RuntimeException("Role not found");
        }
        user.setRole(role);

        masteruserRepository.persist(user);
    }
 @Transactional
public void logout(String token) {
    String tokenHash = DigestUtils.sha256Hex(token);
    UserSession session = userSessionRepository.findBySessionToken(tokenHash);
    if (session != null) {
        session.setStatus(SessionStatusEnum.Logout);
        session.setLogoutAt(LocalDateTime.now());
        userSessionRepository.persist(session);
    }
}

}
