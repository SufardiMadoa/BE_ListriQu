package org.listriqu.entity;

import jakarta.persistence.*;
import org.listriqu.enums.StatusEnum;
import java.time.LocalDateTime;

@Entity
@Table(name = "master_menu")
public class MasterMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Integer menuId;

    @Column(name = "menu_code", unique = true, nullable = false, length = 50)
    private String menuCode;

    @Column(name = "menu_name", nullable = false, length = 100)
    private String menuName;

    @Column(name = "menu_icon", length = 100)
    private String menuIcon;

    @Column(name = "menu_url", length = 255)
    private String menuUrl;

    @Column(name = "menu_order")
    private Integer menuOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_active")
    private StatusEnum isActive;

    @Column(name = "parent_id")
    private Integer parentId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Integer createdBy;
    private Integer updatedBy;

    // Getters Setters
    public Integer getMenuId() { return menuId; }
    public void setMenuId(Integer menuId) { this.menuId = menuId; }

    public String getMenuCode() { return menuCode; }
    public void setMenuCode(String menuCode) { this.menuCode = menuCode; }

    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }

    public String getMenuIcon() { return menuIcon; }
    public void setMenuIcon(String menuIcon) { this.menuIcon = menuIcon; }

    public String getMenuUrl() { return menuUrl; }
    public void setMenuUrl(String menuUrl) { this.menuUrl = menuUrl; }

    public Integer getMenuOrder() { return menuOrder; }
    public void setMenuOrder(Integer menuOrder) { this.menuOrder = menuOrder; }

    public StatusEnum getIsActive() { return isActive; }
    public void setIsActive(StatusEnum isActive) { this.isActive = isActive; }

    public Integer getParentId() { return parentId; }
    public void setParentId(Integer parentId) { this.parentId = parentId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Integer getCreatedBy() { return createdBy; }
    public void setCreatedBy(Integer createdBy) { this.createdBy = createdBy; }

    public Integer getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(Integer updatedBy) { this.updatedBy = updatedBy; }
}
