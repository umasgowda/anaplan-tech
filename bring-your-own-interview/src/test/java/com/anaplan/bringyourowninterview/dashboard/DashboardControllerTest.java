package com.anaplan.bringyourowninterview.dashboard;


import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DashboardControllerTest {

    private static final int ID = 5;
    private static final String TITLE = "new title";
    private static final LocalDateTime now = LocalDateTime.now();

    @Mock
    private DashboardService dashboardService;

    @InjectMocks
    private DashboardController dashboardController;

    @Test
    public void listReturnsSuccessResponseWithDashboards() {

        List<Dashboard> dashboardList = Lists.list(Dashboard.builder().id(ID).title(TITLE).createdAt(now).updatedAt(now).build());
        when(dashboardService.list()).thenReturn(dashboardList);

        ResponseEntity<List<Dashboard>> result = dashboardController.list();

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody(), dashboardList);
    }

    @Test
    public void getReturnsSuccessResponseWithDashboardDataWhenDataExistForGivenId() {
        Dashboard dashboard = Dashboard.builder().id(ID).build();
        when(dashboardService.get(ID)).thenReturn(dashboard);

        ResponseEntity<Dashboard> result = dashboardController.get(ID);

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody(), dashboard);
    }

    @Test
    public void getReturnsNotFoundResponseWhenDataDoesNotExistForGivenId() {
        when(dashboardService.get(ID)).thenReturn(null);

        ResponseEntity<Dashboard> result = dashboardController.get(ID);

        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
        assertNull(result.getBody());
    }

    @Test
    public void postCallsServiceToCreatesAnEntry() {
        Dashboard dashboard = new Dashboard();

        ResponseEntity<Dashboard> dashboardResponse = dashboardController.post(dashboard);

        assertEquals(HttpStatus.NO_CONTENT, dashboardResponse.getStatusCode());
        verify(dashboardService).save(dashboard);
    }

    @Test
    public void putCallsServiceToUpdateAnEntry() {
        Dashboard dashboard = Dashboard.builder().title(TITLE).build();

        ResponseEntity<Dashboard> result = dashboardController.put(ID, dashboard);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        verify(dashboardService).update(ID, TITLE);
    }

    @Test
    public void deleteReturns200ResponseAndSuccessfullyDeletesWhenDataExist() {
        Dashboard dashboard = Dashboard.builder().id(ID).build();
        when(dashboardService.get(ID)).thenReturn(dashboard);
        ResponseEntity<String> result = dashboardController.delete(ID);

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        verify(dashboardService).delete(dashboard);
    }

    @Test
    public void deleteReturnsNotFoundResponseAndDoesNotDeleteAnyRecordWhenDataDoesNotExist() {
        when(dashboardService.get(ID)).thenReturn(null);
        ResponseEntity<String> result = dashboardController.delete(ID);

        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
        verify(dashboardService, never()).delete(any(Dashboard.class));
    }

}