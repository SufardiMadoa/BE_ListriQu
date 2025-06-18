package org.listriqu.dto;

import java.util.List;

public class AssignRoleMenuRequest {
    public Integer role_id;
    public List<Integer> menu_ids;
    public Integer getRole_id() {
        return role_id;
    }
    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }
    public List<Integer> getMenu_ids() {
        return menu_ids;
    }
    public void setMenu_ids(List<Integer> menu_ids) {
        this.menu_ids = menu_ids;
    }

    // Getters & Setters
}