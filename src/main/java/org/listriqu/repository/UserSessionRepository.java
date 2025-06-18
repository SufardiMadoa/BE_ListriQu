package org.listriqu.repository;

import org.listriqu.entity.UserSession;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserSessionRepository implements PanacheRepositoryBase<UserSession, Long> {
   public UserSession findBySessionToken(String tokenHash) {
    return find("sessionToken", tokenHash).firstResult();
}
}
