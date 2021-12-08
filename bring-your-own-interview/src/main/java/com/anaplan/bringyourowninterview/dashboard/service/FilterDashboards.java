package com.anaplan.bringyourowninterview.dashboard.service;


import com.anaplan.bringyourowninterview.dashboard.data.DashboardEntity;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Filtering
 * GET /dashboards?title=Maps
 * GET /dashboards?createdAt=2020-10-11
 */
@Component
@Slf4j
public class FilterDashboards {

    private static final String TITLE = "title";
    private static final String CREATED_AT = "createdAt";
    private static final String UPDATED_AT = "updatedAt";

    public List<DashboardEntity> filterDashboards(Map<String, String> queryParamsMap, List<DashboardEntity> dashboardEntityList) {
        if (queryParamsMap.containsKey(TITLE) || queryParamsMap.containsKey(CREATED_AT) || queryParamsMap.containsKey(UPDATED_AT)) {
            dashboardEntityList = filterByTitle(dashboardEntityList, queryParamsMap.get(TITLE));
            dashboardEntityList = filterByCreatedDate(dashboardEntityList, queryParamsMap.get(CREATED_AT));
            dashboardEntityList = filterByUpdatedDate(dashboardEntityList, queryParamsMap.get(UPDATED_AT));
            return dashboardEntityList;
        }
        return dashboardEntityList;
    }

    private List<DashboardEntity> filterByTitle(List<DashboardEntity> dashboardEntityList, String qpTitleValue) {
        if (qpTitleValue != null) {
            return dashboardEntityList.stream().filter(dashboard -> qpTitleValue.equalsIgnoreCase(dashboard.getTitle())).collect(Collectors.toList());
        }
        return dashboardEntityList;
    }

    private List<DashboardEntity> filterByCreatedDate(List<DashboardEntity> dashboardEntityList, String qpValue) {
        if (qpValue != null) {
            LocalDate givenDate = LocalDate.parse(qpValue);
            return dashboardEntityList.stream().filter(dashboard -> givenDate.equals(dashboard.getCreatedAt().toLocalDate())).collect(Collectors.toList());
        }
        return dashboardEntityList;
    }

    private List<DashboardEntity> filterByUpdatedDate(List<DashboardEntity> dashboardEntityList, String qpValue) {
        if (qpValue != null) {
            LocalDate givenDate = LocalDate.parse(qpValue);
            return dashboardEntityList.stream().filter(dashboard -> givenDate.equals(dashboard.getUpdatedAt().toLocalDate())).collect(Collectors.toList());
        }
        return dashboardEntityList;
    }


}
