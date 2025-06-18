package org.listriqu.response;


public class MenuResponse {
    private Integer menuId;
    private Integer parentId;
    private String menuCode;
    private String menuName;
    private String menuIcon;
    private String menuUrl;
    private Integer menuOrder;
    private String isActive;
    public Integer getMenuId() {
        return menuId;
    }
    public void setMenuId (Integer menuId) {
        this.menuId = menuId;
    }
    public Integer getParentId() {
        return parentId;
    }
    public void setParentId (Integer parentId) {
        this.parentId = parentId;
    }
    public String getMenuCode() {
        return menuCode;
    }
    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }
    public String getMenuName() {
        return menuName;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    public String getMenuIcon() {
        return menuIcon;
    }
    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }
    public String getMenuUrl() {
        return menuUrl;
    }
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }
    public Integer getMenuOrder() {
        return menuOrder;
    }
    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }
    public String getIsActive() {
        return isActive;
    }
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    // Getters & Setters
}
