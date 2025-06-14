package org.listriqu.service;

import java.util.List;

import org.listriqu.dto.MenuDTO;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MenuService {
    
    // dummy dulu, nanti kita buatkan yang benar-benar nested
    public List<MenuDTO> getMenuByRole(Integer roleId) {
        // TODO: ambil dari database berdasarkan role
        return List.of();
    }
}
