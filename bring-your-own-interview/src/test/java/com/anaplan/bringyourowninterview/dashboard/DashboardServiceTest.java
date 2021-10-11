package com.anaplan.bringyourowninterview.dashboard;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DashboardServiceTest {

    private static Integer ID = 5;
    private static String TITLE_1 = "title1";
    private static String TITLE_2 = "title2";

    @Mock
    private DashboardRepository dashboardRepository;

    @InjectMocks
    private DashboardService dashboardService;

    @Test
    public void savesDataToRepository() {
        Dashboard newDashboard = new Dashboard();
        when(dashboardRepository.save(newDashboard)).thenReturn(Dashboard.builder().id(ID).build());

        Dashboard result = dashboardService.save(newDashboard);

        assertNotNull(result);
        assertEquals(ID, result.getId());
    }

    @Test
    public void updateSavesTheDataToRepository() {
        Dashboard dashboard = Dashboard.builder().id(ID).title(TITLE_1).build();
        when(dashboardRepository.findById(ID)).thenReturn(Optional.of(dashboard));
        when(dashboardRepository.save(dashboard)).thenReturn(dashboard);

        Dashboard returned = dashboardService.update(ID, TITLE_2);

        assertNotNull(returned);
        assertEquals(ID, returned.getId());
        assertEquals(TITLE_2, returned.getTitle());
    }

    @Test
    public void listCallsRepository() {
        Dashboard dashboard1 = Dashboard.builder().id(ID).title(TITLE_1).build();
        Dashboard dashboard2 = Dashboard.builder().id(2).title(TITLE_2).build();
        List<Dashboard> dashboards = Arrays.asList(dashboard1, dashboard2);
        when(dashboardRepository.findAll()).thenReturn(dashboards);

        List<Dashboard> list = dashboardService.list();
        assertNotNull(list);
        assertEquals(list.size(), 2);
    }

    @Test
    public void getReturnsDataWhenItExistForGivenId() {
        Dashboard dashboard = new Dashboard();
        Optional<Dashboard> optional = Optional.of(dashboard);
        when(dashboardRepository.findById(ID)).thenReturn(optional);

        Dashboard returned = dashboardService.get(ID);
        assertNotNull(returned);
        assertEquals(dashboard, returned);
    }

    @Test
    public void getReturnsNullWhenDataDoesNotExistForGivenId() {
        when(dashboardRepository.findById(ID)).thenReturn(Optional.empty());

        Dashboard returned = dashboardService.get(ID);
        assertNull(returned);
    }

    @Test
    public void deleteCallsDeleteRepository() {
        Dashboard dashboard = Dashboard.builder().id(ID).build();
        dashboardService.delete(dashboard);

        verify(dashboardRepository).delete(dashboard);
    }

}