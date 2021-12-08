package com.anaplan.bringyourowninterview.dashboard.service;

import com.anaplan.bringyourowninterview.dashboard.web.exception.DashboardNotFoundException;
import com.anaplan.bringyourowninterview.dashboard.web.model.Dashboard;
import com.anaplan.bringyourowninterview.dashboard.data.DashboardEntity;
import com.anaplan.bringyourowninterview.dashboard.data.DashboardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    public List<Dashboard> list() {
        log.debug("In DashboardService - list");
        List<DashboardEntity> dashboardEntities = dashboardRepository.findAll();
        return dashboardEntities.stream().map(this::mapToDashboard).collect(Collectors.toList());
    }

    public Dashboard get(Integer id) {
        Optional<DashboardEntity> dashboardEntity = dashboardRepository.findById(id);
        if (dashboardEntity.isPresent()) {
            log.debug("In DashboardService::Get - dashboard found by id {}", id);
            return mapToDashboard(dashboardEntity.get());
        } else {
            log.error("In DashboardService::Get - dashboard not found for id {}", id);
            throw new DashboardNotFoundException();
        }
    }

    public DashboardEntity save(Dashboard dashboard) {
        DashboardEntity dashboardEntity = mapFromDashboard(dashboard);
        DashboardEntity dashboardEntitySaved = dashboardRepository.save(dashboardEntity);
        log.debug("In DashboardService::save - Successfully saved {}", dashboardEntitySaved);
        return dashboardEntitySaved;
    }

    public void update(Integer id, String title) {
        Optional<DashboardEntity> optionalDashboard = dashboardRepository.findById(id);
        if (optionalDashboard.isPresent()) {
            log.debug("In DashboardService::update - Updating a dashboard for id {}", id);
            DashboardEntity existingDashboardEntity = optionalDashboard.get();
            existingDashboardEntity.setTitle(title);
            existingDashboardEntity.setUpdatedAt(LocalDateTime.now());
             dashboardRepository.save(existingDashboardEntity);
        } else {
            log.error("In DashboardService::update - dashboard not found for id {}", id);
            throw new DashboardNotFoundException();
        }
    }

    public void delete(Integer id) {
        Optional<DashboardEntity> dashboardEntity = dashboardRepository.findById(id);
        if (dashboardEntity.isPresent()) {
            dashboardRepository.delete(dashboardEntity.get());
            log.debug("In DashboardService::delete - Successfully deleted the dashboard {}", dashboardEntity);
        } else {
            log.error("In DashboardService::delete - dashboard not found for id {}", id);
            throw new DashboardNotFoundException();
        }
    }

    private Dashboard mapToDashboard(DashboardEntity dashboardEntity) {
        return Dashboard.builder()
                .id(dashboardEntity.getId())
                .title(dashboardEntity.getTitle())
                .updatedAt(dashboardEntity.getUpdatedAt())
                .createdAt(dashboardEntity.getCreatedAt())
                .build();
    }

    private DashboardEntity mapFromDashboard(Dashboard dashboard) {
        return DashboardEntity.builder()
                .id(dashboard.getId())
                .title(dashboard.getTitle())
                .updatedAt(dashboard.getUpdatedAt())
                .createdAt(dashboard.getCreatedAt())
                .build();
    }
}
