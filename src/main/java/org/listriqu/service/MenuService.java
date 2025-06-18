package org.listriqu.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.listriqu.dto.MenuRequest;
import org.listriqu.entity.MasterMenu;
import org.listriqu.entity.MasterRole;
import org.listriqu.entity.RoleMenu;
import org.listriqu.enums.StatusEnum;
import org.listriqu.repository.MasterRoleRepository;
import org.listriqu.repository.MenuRepository;
import org.listriqu.repository.RoleMenuRepository;
import org.listriqu.response.MenuResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;

@ApplicationScoped
public class MenuService {

    @Inject
    MasterRoleRepository masterRoleRepository;

    @Inject
    RoleMenuRepository roleMenuRepository;

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<MenuResponse> getAllMenus() {
        try {
            return menuRepository.listAll()
                    .stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get menus", e);
        }
    }

    public MenuResponse getMenuById(Integer id) {
        MasterMenu menu = menuRepository.findById(id);
        return toResponse(menu);
    }

    private MenuResponse toResponse(MasterMenu menu) {
        MenuResponse response = new MenuResponse();
        response.setMenuId(menu.getMenuId());
        response.setParentId(menu.getParentId());
        response.setMenuCode(menu.getMenuCode());
        response.setMenuName(menu.getMenuName());
        response.setMenuIcon(menu.getMenuIcon());
        response.setMenuUrl(menu.getMenuUrl());
        response.setMenuOrder(menu.getMenuOrder());
        response.setIsActive(
                menu.getIsActive() != null ? menu.getIsActive().name() : StatusEnum.Inactive.name());
        return response;
    }

    @Transactional
    public void createMenu(MenuRequest request) {
        MasterMenu menu = new MasterMenu();
        menu.setParentId(request.getParent_id() != null ? request.getParent_id().intValue() : null);
        menu.setMenuCode(request.getMenu_code());
        menu.setMenuName(request.getMenu_name());
        menu.setMenuIcon(request.getMenu_icon());
        menu.setMenuUrl(request.getMenu_url());
        menu.setMenuOrder(request.getMenu_order());
        menu.setCreatedAt(LocalDateTime.now());
        menu.setUpdatedAt(LocalDateTime.now());
        menu.setIsActive(StatusEnum.Active);
        menuRepository.persist(menu);

        // Setelah berhasil insert menu, assign otomatis ke SUPERADMIN
        MasterRole superAdminRole = masterRoleRepository.find("roleCode", "SUPERADMIN").firstResult();
        if (superAdminRole != null) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRole(superAdminRole);
            roleMenu.setMenu(menu);
            roleMenu.setIsActive(StatusEnum.Active);
            roleMenu.setCreatedAt(LocalDateTime.now());
            roleMenu.setUpdatedAt(LocalDateTime.now());
            roleMenuRepository.persist(roleMenu);
        }
    }

    @Transactional
    public void updateMenu(Integer menuId, MenuRequest request) {
        MasterMenu menu = menuRepository.findById(menuId);
        if (menu == null) {
            throw new IllegalArgumentException("Menu not found");
        }
        menu.setMenuCode(request.getMenu_code());
        menu.setMenuName(request.getMenu_name());
        menu.setMenuIcon(request.getMenu_icon());
        menu.setMenuUrl(request.getMenu_url());
        menu.setMenuOrder(request.getMenu_order());
        menu.setParentId(request.getParent_id() != null ? request.getParent_id().intValue() : null);
        menu.setUpdatedAt(LocalDateTime.now());
    }

    @Transactional
    public void deleteMenu(Integer menuId) {
        MasterMenu menu = menuRepository.findById(menuId);
        if (menu != null) {
            // Cek apakah menuId sedang direferensikan oleh entri lain
            boolean isReferenced = menuRepository.isMenuIdReferencedAsParent(menuId);
            if (isReferenced) {
                throw new WebApplicationException("Gagal menghapus: menu masih digunakan sebagai parent oleh menu lain",
                        409);
            }

            menuRepository.delete(menu);
        } else {
            throw new WebApplicationException("Menu tidak ditemukan", 404);
        }
    }

}
