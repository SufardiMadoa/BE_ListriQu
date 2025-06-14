package org.listriqu.entity;

import java.time.LocalDateTime;

import org.listriqu.enums.SessionStatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_sessions")
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Integer sessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private MasterUser user;

    @Column(name = "session_token", unique = true, nullable = false)
    private String sessionToken;

    @Column(name = "login_at")
    private LocalDateTime loginAt = LocalDateTime.now();

    @Column(name = "logout_at")
    private LocalDateTime logoutAt;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SessionStatusEnum status = SessionStatusEnum.Active;

    // Getters & Setters
    public Integer getSessionId() { return sessionId; }
    public void setSessionId(Integer sessionId) { this.sessionId = sessionId; }

    public MasterUser getUser() { return user; }
    public void setUser(MasterUser user) { this.user = user; }

    public String getSessionToken() { return sessionToken; }
    public void setSessionToken(String sessionToken) { this.sessionToken = sessionToken; }

    public LocalDateTime getLoginAt() { return loginAt; }
    public void setLoginAt(LocalDateTime loginAt) { this.loginAt = loginAt; }

    public LocalDateTime getLogoutAt() { return logoutAt; }
    public void setLogoutAt(LocalDateTime logoutAt) { this.logoutAt = logoutAt; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }

    public SessionStatusEnum getStatus() { return status; }
    public void setStatus(SessionStatusEnum status) { this.status = status; }
}
