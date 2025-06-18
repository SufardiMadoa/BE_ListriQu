package org.listriqu.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import org.listriqu.entity.MasterMenu;
import org.listriqu.entity.RoleMenu;

import java.util.List;

@ApplicationScoped
public class RoleMenuRepository implements PanacheRepository<RoleMenu> {

    public List<RoleMenu> findByRoleId(Integer roleId) {
        return list("role.roleId = ?1 AND isActive = 'Active'", roleId);
    }

       public List<MasterMenu> getMenuByRoleId(Integer roleId) {
        return getEntityManager()
                .createQuery("""
                    SELECT m FROM Menu m
                    JOIN RoleMenu rm ON m.menuId = rm.menu.menuId
                    WHERE rm.role.roleId = :roleId
                      AND m.isActive = 'Active'
                      AND rm.isActive = 'Active'
                """, MasterMenu.class)
                .setParameter("roleId", roleId)
                .getResultList();
    }
}
