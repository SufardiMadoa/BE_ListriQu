package org.listriqu.dto;

import java.util.List;

import org.listriqu.entity.MasterMenu;

public class MenuDTO {
    public Integer menuId;
    public String menuName;
    public String menuCode;
    public String menuUrl;
    public String menuIcon;
    public Integer parentId;
    public Integer menuOrder;
    public List<MenuDTO> children;


    public static MenuDTO toDTO(MasterMenu menu) {
    MenuDTO dto = new MenuDTO();
    dto.menuId = menu.getMenuId();
    dto.menuName = menu.getMenuName();
    dto.menuCode = menu.getMenuCode();
    dto.menuUrl = menu.getMenuUrl();
    dto.menuIcon = menu.getMenuIcon();
    dto.parentId = menu.getParentId();
    dto.menuOrder = menu.getMenuOrder();
    return dto;
}
}
