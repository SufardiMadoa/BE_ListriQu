package org.listriqu.dto;

public class MasterUnitRequest {
    public String unitCode;
    public String unitName;
    public String address;
    public Integer parentUnitId;
    public Integer managerId;
    public String status; // TAMBAHAN
      public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
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
    public Integer getParentUnitId() {
        return parentUnitId;
    }
    public void setParentUnitId(Integer parentUnitId) {
        this.parentUnitId = parentUnitId;
    }
    public Integer getManagerId() {
        return managerId;
    }
    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}