package org.listriqu.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.listriqu.enums.StatusEnum;

@Entity
@Table(name = "role_menu", uniqueConstraints = @UniqueConstraint(columnNames = {"role_id", "menu_id"}))
public class RoleMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_menu_id")
    private Integer roleMenuId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private MasterRole role;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private MasterMenu menu;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_active")
    private StatusEnum isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getter Setter
    public Integer getRoleMenuId() { return roleMenuId; }
    public void setRoleMenuId(Integer roleMenuId) { this.roleMenuId = roleMenuId; }

    public MasterRole getRole() { return role; }
    public void setRole(MasterRole role) { this.role = role; }

    public MasterMenu getMenu() { return menu; }
    public void setMenu(MasterMenu menu) { this.menu = menu; }

    public StatusEnum getIsActive() { return isActive; }
    public void setIsActive(StatusEnum isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
