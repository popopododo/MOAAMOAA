package com.ssafy.moamoa;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.ssafy.moamoa.service.InitService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InitDb {

	private final InitService initService;

	@PostConstruct
	public void init() {
		initService.addTechstackCatagory();
		initService.addArea();
		initService.addUser();
	}
}