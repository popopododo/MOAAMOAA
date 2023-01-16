package com.ssafy.moamoa.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class ProjectTechStack {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "project_stck_no")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tech_stack_no")
    @NotNull
    private TechStack techStack;

    // project mapping
}
