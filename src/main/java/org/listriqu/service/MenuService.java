package org.listriqu.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.listriqu.dto.MenuRequest;
import org.listriqu.entity.MasterMenu;
import org.listriqu.enums.StatusEnum;
import org.listriqu.repository.MenuRepository;
import org.listriqu.response.MenuResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<MenuResponse> getAllMenus() {
        return menuRepository.listAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public MenuResponse getMenuById(Long id) {
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

        menuRepository.persist(menu);
    }

    @Transactional
    public void updateMenu(Long menuId, MenuRequest request) {
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
    public void deleteMenu(Long menuId) {
        MasterMenu menu = menuRepository.findById(menuId);
        if (menu != null) {
            menuRepository.delete(menu);
        }
    }
}
