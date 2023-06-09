package com.ssafy.moamoa.controller;

import com.ssafy.moamoa.domain.dto.TechStackForm;
import com.ssafy.moamoa.service.TechStackService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/techstack")
@Transactional
public class TechStackController {


	private final TechStackService techStackService;

	@ApiOperation(value = "기술스택 검색",
			notes = "기술스택을 검색하여 결과를 반환해줍니다.")
	@GetMapping("/list/{techName}")
	public ResponseEntity<?> searchTechStack(@PathVariable String techName) {

		List<TechStackForm> techStackFormList = techStackService.searchTechStackByName(techName);
		return new ResponseEntity<List<TechStackForm>>(techStackFormList, HttpStatus.OK);
	}


}
