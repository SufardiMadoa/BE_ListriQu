package org.listriqu.repository;

import org.listriqu.entity.MasterRole;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MasterRoleRepository implements PanacheRepositoryBase<MasterRole, Integer> {
}
