package org.listriqu.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.listriqu.entity.MasterMenu;

@ApplicationScoped
public class MenuRepository implements PanacheRepository<MasterMenu> {
}