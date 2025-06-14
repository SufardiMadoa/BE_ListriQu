package org.listriqu.repository;

import org.listriqu.entity.MasterUser;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MasterUserRepository implements PanacheRepository<MasterUser> {

    public MasterUser findByUsername(String username) {
        return find("username", username).firstResult();
    }
}
