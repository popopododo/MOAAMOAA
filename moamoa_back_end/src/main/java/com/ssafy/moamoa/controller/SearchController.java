package com.ssafy.moamoa.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.moamoa.domain.dto.FilterDto;
import com.ssafy.moamoa.domain.dto.ProfileResultDto;
import com.ssafy.moamoa.domain.dto.ProjectResultDto;
import com.ssafy.moamoa.domain.dto.SearchCondition;
import com.ssafy.moamoa.service.SearchService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {

	private final SearchService searchService;

	@ApiOperation(value = "프로젝트 조회", notes = "프로젝트 목록을 조건에 맞게 검색합니다.", response = ProjectResultDto.class, responseContainer = "List")
	@GetMapping("/project")
	public ResponseEntity<?> searchProject(SearchCondition condition, String cursorId, Pageable pageable) {
		log.debug(condition.toString());
		List<ProjectResultDto> results = searchService.searchProject(condition, cursorId, pageable);
		if (results.isEmpty()) {
			return new ResponseEntity<>(results, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(results, HttpStatus.OK);
	}

	@ApiOperation(value = "프로필 조회", notes = "프로필 목록을 조건에 맞게 검색합니다.", response = ProfileResultDto.class, responseContainer = "List")
	@GetMapping("/profile")
	public ResponseEntity<?> searchProfile(SearchCondition condition, String cursorId, Pageable pageable) {
		List<ProfileResultDto> results = searchService.searchProfile(condition, cursorId, pageable);
		if (results.isEmpty()) {
			return new ResponseEntity<>(results, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(results, HttpStatus.OK);
	}

	@ApiOperation(value = "검색 필터 조회", notes = "검색에 필요한 필터 데이터를 리턴", response = FilterDto.class)
	@GetMapping()
	public ResponseEntity<?> getSearchFilter() {
		FilterDto searchFilter = searchService.getSearchFilter();
		return new ResponseEntity<>(searchFilter, HttpStatus.OK);
	}
}
