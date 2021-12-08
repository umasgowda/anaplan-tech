package com.anaplan.bringyourowninterview.dashboard.web;

import com.anaplan.bringyourowninterview.dashboard.service.DashboardService;
import com.anaplan.bringyourowninterview.dashboard.web.model.Dashboard;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/anaplan")
@Api(value = "dashboards")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping(value = "/dashboards", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List the Dashboards", response = Dashboard.class)
    public ResponseEntity<List<Dashboard>> list() {
        log.info("Dashboard list");

        List<Dashboard> dashboards = dashboardService.list();
        return new ResponseEntity<>(dashboards, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/dashboards", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get Dashboard", response = Dashboard.class)
    public ResponseEntity<Dashboard> get(@PathVariable(name = "id") Integer id) {
        log.info("Dashboard get {}", id);

        Dashboard dashboard = dashboardService.get(id);
        return new ResponseEntity<>(dashboard, HttpStatus.OK);
    }

    @PostMapping(value = "/dashboards")
    @ApiOperation(value = "Creates a Dashboard", response = Dashboard.class)
    public ResponseEntity<Dashboard> post(@RequestBody Dashboard dashboard) {
        log.info("Dashboard post {} ", dashboard);

        dashboardService.save(dashboard);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}/dashboards")
    @ApiOperation(value = "Updates Dashboard")
    public ResponseEntity<Dashboard> put(@PathVariable(name = "id") Integer id, @RequestBody Dashboard dashboard) {
        log.info("Dashboard put id {}, dashboard {}", id, dashboard);

        dashboardService.update(id, dashboard.getTitle());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/dashboards")
    @ApiOperation(value = "Deletes Dashboard", response = Dashboard.class)
    public ResponseEntity<String> delete(@PathVariable(name = "id") Integer id) {
        log.info("Dashboard delete id {}", id);
        dashboardService.delete(id);
        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

}
