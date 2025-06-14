package org.listriqu.dto;

import java.util.List;

public class MenuDTO {
    private Integer menu_id;
    private String menu_code;
    private String menu_name;
    private String menu_icon;
    private String menu_url;
    private Integer menu_order;
    private List<MenuDTO> children; // Nested sub menu

    public MenuDTO() {}

    // Getters and Setters
    public Integer getMenu_id() { return menu_id; }
    public void setMenu_id(Integer menu_id) { this.menu_id = menu_id; }

    public String getMenu_code() { return menu_code; }
    public void setMenu_code(String menu_code) { this.menu_code = menu_code; }

    public String getMenu_name() { return menu_name; }
    public void setMenu_name(String menu_name) { this.menu_name = menu_name; }

    public String getMenu_icon() { return menu_icon; }
    public void setMenu_icon(String menu_icon) { this.menu_icon = menu_icon; }

    public String getMenu_url() { return menu_url; }
    public void setMenu_url(String menu_url) { this.menu_url = menu_url; }

    public Integer getMenu_order() { return menu_order; }
    public void setMenu_order(Integer menu_order) { this.menu_order = menu_order; }

    public List<MenuDTO> getChildren() { return children; }
    public void setChildren(List<MenuDTO> children) { this.children = children; }
}
