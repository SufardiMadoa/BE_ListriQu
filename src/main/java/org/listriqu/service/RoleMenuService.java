package org.listriqu.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.listriqu.dto.MenuDTO;
import org.listriqu.entity.MasterMenu;
import org.listriqu.entity.RoleMenu;
import org.listriqu.repository.RoleMenuRepository;

import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class RoleMenuService {

    @Inject
    RoleMenuRepository roleMenuRepository;

    public List<MenuDTO> getMenusByRole(Integer roleId) {
        List<RoleMenu> roleMenus = roleMenuRepository.findByRoleId(roleId);
        List<MasterMenu> menus = roleMenus.stream()
                .map(RoleMenu::getMenu)
                .collect(Collectors.toList());

        // Build map parentId -> list of child menus
        Map<Integer, List<MenuDTO>> menuMap = new HashMap<>();
        for (MasterMenu menu : menus) {
            MenuDTO dto = convertToDTO(menu);
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
        return dto;
    }
}