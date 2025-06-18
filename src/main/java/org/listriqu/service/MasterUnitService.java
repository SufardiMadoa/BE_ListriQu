package org.listriqu.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.listriqu.dto.MasterUnitRequest;
import org.listriqu.entity.MasterUnit;
import org.listriqu.enums.StatusEnum;
import org.listriqu.repository.MasterUnitRepository;
import org.listriqu.repository.MasterUserRepository;
import org.listriqu.response.UnitResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MasterUnitService {

    @Inject
    MasterUnitRepository unitRepository;

    @Inject
    MasterUserRepository userRepository;

    private void validateRequest(MasterUnitRequest request) {
        if (request.getUnitName() == null || request.getUnitName().trim().isEmpty()) {
            throw new IllegalArgumentException("Unit name is required");
        }

        if (request.getStatus() != null) {
            // Validasi apakah statusnya valid
            StatusEnum.fromValue(request.getStatus());
        }
    }

    @Transactional
    public void create(MasterUnitRequest request) {
        validateRequest(request);
        MasterUnit unit = new MasterUnit();
        unit.setUnitCode(request.unitCode);
        unit.setUnitName(request.unitName);
        unit.setAddress(request.address);
        unit.setCreatedAt(LocalDateTime.now());
        unit.setUpdatedAt(LocalDateTime.now());

        // Status Enum
        unit.setStatus(request.status != null
                ? StatusEnum.fromValue(request.status)
                : StatusEnum.Active); // default Active

        if (request.parentUnitId != null) {
            unit.setParentUnit(unitRepository.findById(request.parentUnitId));
        }
        if (request.managerId != null) {
            unit.setManager(userRepository.findById(request.managerId));
        }
        unitRepository.persist(unit);
    }

    public List<UnitResponse> getAllUnits() {
        return unitRepository.listAll().stream().map(this::toResponse).collect(Collectors.toList());
    }
     @Transactional
    public void updateUnit(Integer id, MasterUnitRequest request) {
        validateRequest(request);

        MasterUnit unit = unitRepository.findById(id);
        if (unit == null) throw new RuntimeException("Unit not found");

        unit.setUnitName(request.getUnitName());
        unit.setUpdatedAt(LocalDateTime.now());
        unit.setStatus(StatusEnum.fromValue(request.getStatus()));

        if (request.getParentUnitId() != null) {
            unit.setParentUnit(unitRepository.findById(request.getParentUnitId()));
        } else {
            unit.setParentUnit(null);
        }
        if (request.getManagerId() != null) {
            unit.setManager(userRepository.findById(request.getManagerId()));
        } else {
            unit.setManager(null);
        }
    }

    @Transactional
    public void deleteUnit(Integer id) {
        MasterUnit unit = unitRepository.findById(id);
        if (unit != null) unitRepository.delete(unit);
    }



    private UnitResponse toResponse(MasterUnit unit) {
        UnitResponse resp = new UnitResponse();
        resp.unitId = unit.getUnitId();
        resp.unitCode = unit.getUnitCode();
        resp.unitName = unit.getUnitName();
        resp.description = unit.getDescription();
        resp.address = unit.getAddress();
        resp.phone = unit.getPhone();
        resp.email = unit.getEmail();
        resp.status = unit.getStatus().name();

        if (unit.getParentUnit() != null) {
            resp.parentUnitId = unit.getParentUnit().getUnitId();
        }
        if (unit.getManager() != null) {
            resp.managerId = unit.getManager().getUserId();
        }

        return resp;
    }
}
