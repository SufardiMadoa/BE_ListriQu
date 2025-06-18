package org.listriqu.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.listriqu.entity.MasterUser;
import org.listriqu.entity.RoleMenu;
import org.listriqu.entity.MasterMenu;
import org.listriqu.repository.MasterUserRepository;
import org.listriqu.repository.RoleMenuRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.listriqu.repository.MasterRoleRepository;

@ApplicationScoped
public class UserAccessService {

    @Inject
    MasterUserRepository userRepository;

    @Inject
    RoleMenuRepository roleMenuRepository;

    @Inject
    MasterRoleRepository roleRepository;


    @Transactional
    public Map<String, Object> getUserAccess(Integer userId) {
        MasterUser user = userRepository.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("user_id", user.getUserId());
        response.put("username", user.getUsername());
        response.put("full_name", user.getFullName());

        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("role_id", user.getRole().getRoleId());
        roleMap.put("role_code", user.getRole().getRoleCode());
        roleMap.put("role_name", user.getRole().getRoleName());
        response.put("role", roleMap);

        List<RoleMenu> roleMenus = roleMenuRepository.findByRoleId(user.getRole().getRoleId());
        List<Map<String, Object>> menus = roleMenus.stream().map(rm -> {
            MasterMenu menu = rm.getMenu();
            Map<String, Object> menuMap = new HashMap<>();
            menuMap.put("menu_id", menu.getMenuId());
            menuMap.put("menu_name", menu.getMenuName());
            menuMap.put("menu_url", menu.getMenuUrl());
            return menuMap;
        }).collect(Collectors.toList());
        response.put("menus", menus);

        return response;
    }
    public List<Map<String, Object>> getAllRolesWithUsers() {
        return roleRepository.listAll().stream().map(role -> {
            Map<String, Object> map = new HashMap<>();
            map.put("role_id", role.getRoleId());
            map.put("role_code", role.getRoleCode());
            map.put("role_name", role.getRoleName());

            // Ambil semua user yang memiliki role ini
            List<Map<String, Object>> users = userRepository.find("role.roleId = ?1", role.getRoleId())
                    .stream()
                    .map(user -> {
                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("user_id", user.getUserId());
                        userMap.put("name", user.getFullName());
                        return userMap;
                    }).collect(Collectors.toList());

            map.put("users", users);
            return map;
        }).collect(Collectors.toList());
    }
    

}
