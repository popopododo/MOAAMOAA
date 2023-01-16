package com.ssafy.moamoa.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class ProjectArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_area_no")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "area_no")
    @NotNull
    private Area area;

    // project mapping
    @NotNull
    @ManyToOne
    @JoinColumn(name = "project_no")
    private Project project;
}
