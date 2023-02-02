package com.ssafy.moamoa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.moamoa.domain.entity.ProfileTechStack;
import com.ssafy.moamoa.repository.querydsl.QProfileTechStackRepository;

public interface ProfileTechStackRepository extends JpaRepository<ProfileTechStack, Long>,
	QProfileTechStackRepository {
}