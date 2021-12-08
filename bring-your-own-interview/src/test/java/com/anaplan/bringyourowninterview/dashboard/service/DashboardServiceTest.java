package com.anaplan.bringyourowninterview.dashboard.service;

import com.anaplan.bringyourowninterview.dashboard.data.DashboardEntity;
import com.anaplan.bringyourowninterview.dashboard.data.DashboardRepository;
import com.anaplan.bringyourowninterview.dashboard.web.exception.DashboardNotFoundException;
import com.anaplan.bringyourowninterview.dashboard.web.model.Dashboard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        Dashboard dashboard = Dashboard.builder().id(ID).build();
        DashboardEntity dashboardEntity = DashboardEntity.builder().id(ID).build();
        when(dashboardRepository.save(dashboardEntity)).thenReturn(dashboardEntity);

        DashboardEntity result = dashboardService.save(dashboard);

        assertNotNull(result);
        assertEquals(ID, result.getId());
    }

    @Test
    public void updateSavesTheDataToRepositoryWhenDashboardExistForGivenId() {
        DashboardEntity dashboardEntity = DashboardEntity.builder().id(ID).title(TITLE_2).build();
        when(dashboardRepository.findById(ID)).thenReturn(Optional.of(dashboardEntity));
        when(dashboardRepository.save(dashboardEntity)).thenReturn(dashboardEntity);

        dashboardService.update(ID, TITLE_2);

        verify(dashboardRepository).save(dashboardEntity);
    }

    @Test(expected = DashboardNotFoundException.class)
    public void updateThrowsExceptionWhenDashboardDoesNotExistForGivenId() {
        when(dashboardRepository.findById(ID)).thenReturn(Optional.empty());

        dashboardService.update(ID, TITLE_2);

        verify(dashboardRepository).findById(ID);
    }

    @Test
    public void listCallsRepository() {
        DashboardEntity dashboardEntity1 = DashboardEntity.builder().id(ID).title(TITLE_1).build();
        DashboardEntity dashboardEntity2 = DashboardEntity.builder().id(2).title(TITLE_2).build();
        List<DashboardEntity> dashboardEntities = Arrays.asList(dashboardEntity1, dashboardEntity2);
        when(dashboardRepository.findAll()).thenReturn(dashboardEntities);

        List<Dashboard> list = dashboardService.list();
        assertNotNull(list);
        assertEquals(list.size(), 2);
    }

    @Test
    public void getReturnsDataWhenItExistForGivenId() {
        Optional<DashboardEntity> dashboardEntity = Optional.of(DashboardEntity.builder().id(ID).title(TITLE_1).build());
        when(dashboardRepository.findById(ID)).thenReturn(dashboardEntity);

        Dashboard result = dashboardService.get(ID);
        assertNotNull(result);
    }

    @Test(expected = DashboardNotFoundException.class)
    public void getThrowsExceptionWhenDashboardDoesNotExistForGivenId() {
        when(dashboardRepository.findById(ID)).thenReturn(Optional.empty());

        dashboardService.get(ID);
    }

    @Test
    public void deleteCallsDeleteRepositoryWhenDashboardExistForGivenId() {
        DashboardEntity dashboardEntity = DashboardEntity.builder().id(ID).title(TITLE_1).build();
        when(dashboardRepository.findById(ID)).thenReturn(Optional.of(dashboardEntity));

        dashboardService.delete(ID);

        verify(dashboardRepository).delete(dashboardEntity);
    }

    @Test(expected = DashboardNotFoundException.class)
    public void deleteThrowsExceptionWhenDashboardDoesNotExistForGivenId() {
        when(dashboardRepository.findById(ID)).thenReturn(Optional.empty());

        dashboardService.delete(ID);
    }

}