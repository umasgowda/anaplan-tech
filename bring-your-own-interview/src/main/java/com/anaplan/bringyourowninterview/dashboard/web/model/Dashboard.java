package com.anaplan.bringyourowninterview.dashboard.web.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dashboard {

    private Integer id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String title;

}