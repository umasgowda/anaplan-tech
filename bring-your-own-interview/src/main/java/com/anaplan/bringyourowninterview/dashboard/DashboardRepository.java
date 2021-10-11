package com.anaplan.bringyourowninterview.dashboard;


import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface DashboardRepository extends Repository<Dashboard, Integer> {

    <D extends Dashboard> D save(D dashboard);

    Optional<Dashboard> findById(Integer id);

    Iterable<Dashboard> findAll();

    void delete(Dashboard dashboard);
}
