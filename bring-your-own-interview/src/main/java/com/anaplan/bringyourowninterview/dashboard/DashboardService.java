package com.anaplan.bringyourowninterview.dashboard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    public List<Dashboard> list() {
        log.debug("In DashboardService - list");
        return dashboardRepository.findAll();
    }

    public Dashboard get(Integer id) {
        log.debug("In DashboardService - get by id {}", id);
        Optional<Dashboard> dashboard = dashboardRepository.findById(id);
        return dashboard.orElse(null);
    }

    public Dashboard save(Dashboard dashboard) {
        Dashboard dashboardSaved = dashboardRepository.save(dashboard);
        log.debug("In DashboardService - Successfully saved {}", dashboardSaved);
        return dashboardSaved;
    }

    public Dashboard update(Integer id, String title) {
        Optional<Dashboard> optionalDashboard = dashboardRepository.findById(id);
        if (optionalDashboard.isPresent()) {
            Dashboard existingDashboard = optionalDashboard.get();
            existingDashboard.setTitle(title);
            existingDashboard.setUpdatedAt(LocalDateTime.now());
            log.debug("In DashboardService - Updating a dashboard for id {}", id);
            return save(existingDashboard);
        } else {
            log.debug("In DashboardService - creating a dashboard for id {}", id);
            Dashboard newDashboard = Dashboard.builder().title(title).build();
            return save(newDashboard);
        }
    }

    public void delete(Dashboard dashboard) {
        dashboardRepository.delete(dashboard);
        log.debug("In DashboardService - Successfully deleted the dashboard {}", dashboard);
    }
}
