package org.listriqu.repository;

import org.listriqu.entity.MasterUser;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MasterUserRepository implements PanacheRepositoryBase<MasterUser, Integer> {

    public MasterUser findByUsername(String username) {
        return find("username", username).firstResult();
    }
}