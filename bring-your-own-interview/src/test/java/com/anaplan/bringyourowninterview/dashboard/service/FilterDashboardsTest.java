package com.anaplan.bringyourowninterview.dashboard.service;

import com.anaplan.bringyourowninterview.dashboard.data.DashboardEntity;
import com.google.common.collect.Lists;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class FilterDashboardsTest {

    @InjectMocks
    private FilterDashboards filterDashboards;

    @Test
    public void filterDashboardsFiltersByTitleWhenQueryParamContainsTitle() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("title", "maps");
        DashboardEntity dashboardEntity1 = DashboardEntity.builder().title("Maps").createdAt(LocalDateTime.now()).build();
        DashboardEntity dashboardEntity2 = DashboardEntity.builder().title("ATitle").createdAt(LocalDateTime.now().minusDays(1)).build();
        DashboardEntity dashboardEntity3 = DashboardEntity.builder().title("BTitle").createdAt(LocalDateTime.now().minusDays(2).minusHours(1)).build();
        DashboardEntity dashboardEntity4 = DashboardEntity.builder().title("CTitle").createdAt(LocalDateTime.now().minusDays(3).minusHours(2)).build();
        List<DashboardEntity> dashboardEntityList = Lists.newArrayList(dashboardEntity1, dashboardEntity2, dashboardEntity3, dashboardEntity4);

        List<DashboardEntity> results = filterDashboards.filterDashboards(queryParams, dashboardEntityList);

        assertEquals(results.size(), 1);
        assertEquals(results.get(0), dashboardEntity1);
    }

    @Test
    public void filterDashboardsFiltersByDateWhenQueryParamContainsOnlyDate() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("createdAt", "2021-10-15");
        LocalDateTime localDateTime = LocalDateTime.of(2021, 10, 15, 18, 36, 45);
        DashboardEntity dashboardEntity1 = DashboardEntity.builder().title("Maps").createdAt(localDateTime).build();
        DashboardEntity dashboardEntity2 = DashboardEntity.builder().title("ATitle").createdAt(localDateTime.minusDays(1)).build();
        DashboardEntity dashboardEntity3 = DashboardEntity.builder().title("BTitle").createdAt(localDateTime).build();
        DashboardEntity dashboardEntity4 = DashboardEntity.builder().title("CTitle").createdAt(localDateTime.minusDays(3)).build();
        List<DashboardEntity> dashboardEntityList = Lists.newArrayList(dashboardEntity1, dashboardEntity2, dashboardEntity3, dashboardEntity4);

        List<DashboardEntity> results = filterDashboards.filterDashboards(queryParams, dashboardEntityList);

        assertEquals(results.size(), 2);
        assertThat("results contains items" , results, Matchers.hasItems(dashboardEntity1, dashboardEntity3));
    }

}