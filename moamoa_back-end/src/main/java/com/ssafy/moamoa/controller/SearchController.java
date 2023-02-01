package com.ssafy.moamoa.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.moamoa.domain.dto.ProjectDto;
import com.ssafy.moamoa.domain.dto.SearchCondition;
import com.ssafy.moamoa.service.SearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

	private final SearchService searchService;

	@GetMapping("/project")
	public ResponseEntity<?> searchProject(SearchCondition condition) {
		List<ProjectDto> results = searchService.searchProject(condition);
		return new ResponseEntity<>(results, HttpStatus.OK);
	}

	@GetMapping("/profile")
	public ResponseEntity<?> searchProfile(SearchCondition condition) {
		log.debug(condition.toString());
		return null;
	}
}