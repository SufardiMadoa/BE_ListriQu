package org.listriqu.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.listriqu.dto.MenuDTO;
import org.listriqu.entity.MasterMenu;
import org.listriqu.entity.MasterRole;
import org.listriqu.entity.RoleMenu;
import org.listriqu.enums.StatusEnum;
import org.listriqu.repository.MasterRoleRepository;
import org.listriqu.repository.MenuRepository;
import org.listriqu.repository.RoleMenuRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class RoleMenuService {

    @Inject
    RoleMenuRepository roleMenuRepository;

    @Inject
    MasterRoleRepository masterRoleRepository;

    @Inject
    MenuRepository masterMenuRepository;


    public List<MenuDTO> getMenusByRole(Integer roleId) {
        List<RoleMenu> roleMenus = roleMenuRepository.findByRoleId(roleId);
        List<MasterMenu> menus = roleMenus.stream()
                .map(RoleMenu::getMenu)
                .collect(Collectors.toList());

        // Build map parentId -> list of child menus
        Map<Integer, List<MenuDTO>> menuMap = new HashMap<>();
        for (MasterMenu menu : menus) {
            MenuDTO dto = convertToDTO(menu);
            @SuppressWarnings("UnnecessaryUnboxing")
           Integer parentId = (menu.getParentId() == null) ? 0 : menu.getParentId().intValue();
            menuMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(dto);
        }

        // Build nested menu tree
        return buildMenuTree(menuMap, 0);
    }

    private List<MenuDTO> buildMenuTree(Map<Integer, List<MenuDTO>> map, Integer parentId) {
        List<MenuDTO> list = map.getOrDefault(parentId, new ArrayList<>());
        for (MenuDTO dto : list) {
            dto.children = buildMenuTree(map, dto.menuId);
        }
        return list;
    }

    private MenuDTO convertToDTO(MasterMenu menu) {
        MenuDTO dto = new MenuDTO();
        dto.menuId = menu.getMenuId();
        dto.menuName = menu.getMenuName();
        dto.menuIcon = menu.getMenuIcon();
        dto.menuUrl = menu.getMenuUrl();
        dto.parentId = menu.getParentId();
        dto.menuOrder = menu.getMenuOrder();
        dto.menuCode = menu.getMenuCode();
        return dto;
    }

    @Transactional
    public void assignRoleMenu(Integer roleId, List<Integer> menuIds) {
        MasterRole role = masterRoleRepository.findById(roleId);
        if (role == null) {
            throw new RuntimeException("Role not found");
        }

        // Hapus semua role_menu yang lama untuk role ini
        roleMenuRepository.delete("role.roleId = ?1", roleId);

        // Assign ulang menu baru
        for (Integer menuId : menuIds) {
            MasterMenu menu = masterMenuRepository.findById(menuId);
            if (menu == null) {
                throw new RuntimeException("Menu ID not found: " + menuId);
            }

            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRole(role);
            roleMenu.setMenu(menu);
            roleMenu.setIsActive(StatusEnum.Active);
            roleMenu.setCreatedAt(LocalDateTime.now());
            roleMenu.setUpdatedAt(LocalDateTime.now());

            roleMenuRepository.persist(roleMenu);
        }
    }
}