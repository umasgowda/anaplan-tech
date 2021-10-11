package com.anaplan.bringyourowninterview.dashboard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    public List<Dashboard> list() {
        Iterable<Dashboard> all = dashboardRepository.findAll();

        return StreamSupport.stream(all.spliterator(), false).collect(Collectors.toList());
    }

    public Dashboard get(Integer id) {
        Optional<Dashboard> dashboard = dashboardRepository.findById(id);
        return dashboard.orElse(null);
    }

    public Dashboard save(Dashboard dashboard) {
        Dashboard dashboardSaved = dashboardRepository.save(dashboard);
        log.debug("Successfully saved {}", dashboardSaved);
        return dashboardSaved;
    }

    public Dashboard update(Integer id, String title) {
        Optional<Dashboard> optionalDashboard = dashboardRepository.findById(id);
        if (optionalDashboard.isPresent()) {
            Dashboard existingDashboard = optionalDashboard.get();
            existingDashboard.setTitle(title);
            existingDashboard.setUpdatedAt(LocalDateTime.now());
            log.debug("updating the dashboard for id {}", id);
            return save(existingDashboard);
        } else {
            log.debug("creating the dashboard for id {}", id);
            Dashboard newDashboard = Dashboard.builder().title(title).build();
            return save(newDashboard);
        }
    }

    public void delete(Dashboard dashboard) {
        dashboardRepository.delete(dashboard);
        log.debug("Successfully deleted the dashboard {}", dashboard);
    }
}
