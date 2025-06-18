package org.listriqu.entity;


import java.time.LocalDateTime;

import org.listriqu.enums.StatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "master_unit", uniqueConstraints = @UniqueConstraint(columnNames = "unit_code"))
public class MasterUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Integer unitId;

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MasterUnit getParentUnit() {
        return parentUnit;
    }

    public void setParentUnit(MasterUnit parentUnit) {
        this.parentUnit = parentUnit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MasterUser getManager() {
        return manager;
    }

    public void setManager(MasterUser manager) {
        this.manager = manager;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public MasterUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(MasterUser createdBy) {
        this.createdBy = createdBy;
    }

    public MasterUser getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(MasterUser updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Column(name = "unit_code", nullable = false)
    private String unitCode;

    @Column(name = "unit_name", nullable = false)
    private String unitName;

    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_unit_id")
    private MasterUnit parentUnit;

    private String address;
    private String phone;
    private String email;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private MasterUser manager;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private MasterUser createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private MasterUser updatedBy;

    // Getter & Setter (gunakan lombok kalau mau clean)
}
