package com.anaplan.bringyourowninterview.dashboard.web;


import com.anaplan.bringyourowninterview.dashboard.service.DashboardService;
import com.anaplan.bringyourowninterview.dashboard.web.exception.DashboardNotFoundException;
import com.anaplan.bringyourowninterview.dashboard.web.model.Dashboard;
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
import static org.junit.Assert.assertNotNull;
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
    public void listReturnsSuccessResponseContainsDashboards() {

        List<Dashboard> dashboardList = Lists.list(Dashboard.builder().id(ID).title(TITLE).createdAt(now).updatedAt(now).build());
        when(dashboardService.list()).thenReturn(dashboardList);

        ResponseEntity<List<Dashboard>> result = dashboardController.list();

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody(), dashboardList);
    }

    @Test
    public void getReturnsSuccessResponseContainsDashboardWhenDashboardExistForGivenId() {
        Dashboard dashboard = Dashboard.builder().id(ID).build();
        when(dashboardService.get(ID)).thenReturn(dashboard);

        ResponseEntity<Dashboard> result = dashboardController.get(ID);

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertNotNull(result.getBody());
    }

    @Test(expected = DashboardNotFoundException.class)
    public void getReturnsNotFoundResponseWhenDataDoesNotExistForGivenId() {
        when(dashboardService.get(ID)).thenThrow(new DashboardNotFoundException());

        ResponseEntity<Dashboard> result = dashboardController.get(ID);

        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void postCallsServiceToCreatesAnEntry() {
        Dashboard dashboard = new Dashboard();

        ResponseEntity<Dashboard> dashboardResponse = dashboardController.post(dashboard);

        assertEquals(HttpStatus.NO_CONTENT, dashboardResponse.getStatusCode());
        verify(dashboardService).save(dashboard);
    }

    @Test
    public void putCallsServiceToUpdateAnEntryWhenDashboardExistForGivenId() {
        Dashboard dashboard = Dashboard.builder().title(TITLE).build();

        ResponseEntity<Dashboard> result = dashboardController.put(ID, dashboard);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        verify(dashboardService).update(ID, TITLE);
    }

    @Test(expected = DashboardNotFoundException.class)
    public void putCallsServiceToUpdateAnEntryWhenDashboardDoesNotExistForGivenIdThrowsException() {
        doThrow(new DashboardNotFoundException()).when(dashboardService).update(ID, TITLE);
        Dashboard dashboard = Dashboard.builder().title(TITLE).build();

        ResponseEntity<Dashboard> result = dashboardController.put(ID, dashboard);
        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
        verify(dashboardService).update(ID, TITLE);
    }

    @Test
    public void deleteSuccessfullyDeletesWhenDashboardExist() {

        ResponseEntity<String> result = dashboardController.delete(ID);

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody(), "Successfully deleted");
        verify(dashboardService).delete(ID);
    }

    @Test(expected = DashboardNotFoundException.class)
    public void deleteReturnsNotFoundResponseAndDoesNotDeleteAnyRecordWhenDataDoesNotExist() {
        doThrow(new DashboardNotFoundException()).when(dashboardService).delete(ID);

        ResponseEntity<String> result = dashboardController.delete(ID);
        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

}