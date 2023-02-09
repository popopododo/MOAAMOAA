package com.ssafy.moamoa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.moamoa.domain.dto.SidePjtForm;
import com.ssafy.moamoa.domain.dto.TechStackForm;
import com.ssafy.moamoa.domain.entity.SidePjt;
import com.ssafy.moamoa.repository.ProfileRepository;
import com.ssafy.moamoa.repository.SideProjectRepository;
import com.ssafy.moamoa.repository.SideProjectTechStackRepository;
import com.ssafy.moamoa.repository.TechStackRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class SideProjectService {
	private static final String SUCCESS = "SUCCESS";
	private static final String FAIL = "FAIL";

	private final ProfileRepository profileRepository;

	private final SideProjectRepository sideProjectRepository;

	private final TechStackRepository techStackRepository;

	private final SideProjectTechStackRepository sideProjectTechStackRepository;

	private final TechStackService techStackService;




	// 사이드 프로젝트

	public List<SidePjtForm> getSideProjects(Long profileId) {
		List<SidePjt> sidePjtList = sideProjectRepository.getSideProjectsByIdAsc(profileId);
		List<SidePjtForm> sidePjtFormList = new ArrayList<>();
		for (SidePjt sidePjt : sidePjtList) {
			SidePjtForm sidePjtForm = SidePjtForm.builder()
				.id(sidePjt.getId())
				.name(sidePjt.getName())
				.year(sidePjt.getYear())
				.techStackFormList(techStackService.getSideProjectTechStacks(sidePjt.getId()))
				.context(sidePjt.getContext())
				.build();

			sidePjtFormList.add(sidePjtForm);
		}

		return sidePjtFormList;
	}

	//  profileId 가 아닌 sidePjtForm 의 ID가 들어가야한다.
	public List<SidePjtForm> addSidePjt(Long profileId, SidePjtForm sidePjtForm) {

		SidePjt sidePjt = SidePjt.builder()
			.profile(profileRepository.getProfileById(profileId))
			.name(sidePjtForm.getName())
			.year(sidePjtForm.getYear())
			.context(sidePjtForm.getContext())

			.build();

		SidePjt tempSidePjt  = sideProjectRepository.save(sidePjt);


		// Parsing SideProjectTechStack
		List<TechStackForm> techStackFormList = sidePjtForm.getTechStackFormList();

		techStackService.modifySideProjectTechStack(tempSidePjt.getId(),techStackFormList);

		// Return

		List<SidePjt> sideProjectList = sideProjectRepository.getSideProjectsByIdAsc(profileId);
		List<SidePjtForm> returnList = new ArrayList<>();
		for(SidePjt sp : sideProjectList)
		{
			SidePjtForm tempSidePjtForm = SidePjtForm.builder()
				.id(sp.getId())
				.name(sp.getName())
				.context(sp.getContext())
				.year(sp.getYear())
				.techStackFormList(techStackService.getSideProjectTechStacks(sp.getId())).build();
			returnList.add(tempSidePjtForm);
		}

		return returnList;
	}

	public List<SidePjtForm> modifySidePjt(Long profileId, SidePjtForm sidePjtForm) {

		SidePjt sidePjt = sideProjectRepository.getSideProjectById(sidePjtForm.getId());

		sidePjt.setName(sidePjtForm.getName());
		sidePjt.setContext(sidePjtForm.getContext());
		sidePjt.setYear(sidePjtForm.getYear());



		// Parsing SideProjectTechStack
		List<TechStackForm> techStackFormList = sidePjtForm.getTechStackFormList();

		techStackService.modifySideProjectTechStack(sidePjt.getId(),techStackFormList);


		sideProjectRepository.save(sidePjt);
		// Return

		List<SidePjt> sideProjectList = sideProjectRepository.getSideProjectsByIdAsc(profileId);
		List<SidePjtForm> returnList = new ArrayList<>();
		for(SidePjt sp : sideProjectList)
		{
			SidePjtForm tempSidePjtForm = SidePjtForm.builder()
					.id(sp.getId())
					.name(sp.getName())
					.context(sp.getContext())
					.year(sp.getYear())
					.techStackFormList(techStackService.getSideProjectTechStacks(sp.getId())).build();
			returnList.add(tempSidePjtForm);
		}

		return returnList;
	}

	public List<SidePjtForm> deleteSidePjt(Long profileId, Long projectId) {

		// 외래키 Side Project TechStack 삭제

		sideProjectTechStackRepository.deleteAllSideProjectTechStack(projectId);


		// SideProject 삭제
		Long deleteCount = sideProjectRepository.deleteSideProjectById(projectId);

		// Return

		List<SidePjt> sideProjectList = sideProjectRepository.getSideProjectsByIdAsc(profileId);
		List<SidePjtForm> returnList = new ArrayList<>();
		for(SidePjt sp : sideProjectList)
		{
			SidePjtForm tempSidePjtForm = SidePjtForm.builder()
					.id(sp.getId())
					.name(sp.getName())
					.context(sp.getContext())
					.year(sp.getYear())
					.techStackFormList(techStackService.getSideProjectTechStacks(sp.getId())).build();
			returnList.add(tempSidePjtForm);
		}

		return returnList;
	}
}
