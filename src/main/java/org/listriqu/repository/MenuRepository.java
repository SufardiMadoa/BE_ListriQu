package org.listriqu.repository;


import org.listriqu.entity.MasterMenu;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MenuRepository implements PanacheRepositoryBase<MasterMenu, Integer> {
    public boolean isMenuIdReferencedAsParent(Integer menuId) {
    return count("parentId = ?1", menuId) > 0;
}
}