package com.anaplan.bringyourowninterview.dashboard.service;


import com.anaplan.bringyourowninterview.dashboard.data.DashboardEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Example queries:
 * Sorting
 * GET /dashboards?sort=createdAt:asc
 * GET /dashboards?sort=createdAt:desc
 * GET /dashboards?sort=title:asc
 * GET /dashboards?sort=title:desc
 * GET /dashboards?sort=updatedAt:asc
 * GET /dashboards?sort=updatedAt:desc
 */

@Slf4j
@Component
public class SortDashboards {

    private static final String SORT = "sort";
    private static final String TITLE = "title";
    private static final String CREATED_AT = "createdAt";
    private static final String UPDATED_AT = "updatedAt";
    private static final String ASC = "asc";


    private boolean hasSortKey(Map<String, String> queryParameters) {
        return queryParameters.containsKey(SORT);
    }

    public List<DashboardEntity> sortDashBoards(Map<String, String> queryParamsMap, List<DashboardEntity> dashboardEntityList) {
        if (hasSortKey(queryParamsMap)) {
            String sortValue = queryParamsMap.get(SORT);
            String[] queryParamValues = sortValue.split(":");
            doSort(dashboardEntityList, queryParamValues[0], queryParamValues[1]);
        }
        return dashboardEntityList;
    }

    private void doSort(List<DashboardEntity> dashboardEntityList, String sortByType, String sortOrder) {
        log.info("sort by type {} , order - {}", sortByType, sortOrder);
        if (TITLE.equalsIgnoreCase(sortByType)) {
            sortByTitle(dashboardEntityList, sortOrder);
        } else if (CREATED_AT.equalsIgnoreCase(sortByType)) {
            sortByCreatedDate(dashboardEntityList, sortOrder);
        } else if (UPDATED_AT.equalsIgnoreCase(sortByType)) {
            sortByUpdatedDate(dashboardEntityList, sortOrder);
        }
    }

    private void sortByTitle(List<DashboardEntity> dashboardEntityList, String order) {
        Collections.sort(dashboardEntityList, Comparator.comparing(DashboardEntity::getTitle));
        if (ASC.equalsIgnoreCase(order)) {
            Collections.reverse(dashboardEntityList);
        }
    }

    private void sortByCreatedDate(List<DashboardEntity> dashboardEntityList, String order) {
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Collections.sort(dashboardEntityList, Comparator.comparing(o -> o.getCreatedAt().format(fm)));
        if (ASC.equalsIgnoreCase(order)) {
            Collections.reverse(dashboardEntityList);
        }
    }

    private void sortByUpdatedDate(List<DashboardEntity> dashboardEntityList, String order) {
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Collections.sort(dashboardEntityList, Comparator.comparing(o -> o.getUpdatedAt().format(fm)));
        if (ASC.equalsIgnoreCase(order)) {
            Collections.reverse(dashboardEntityList);
        }
    }

}
