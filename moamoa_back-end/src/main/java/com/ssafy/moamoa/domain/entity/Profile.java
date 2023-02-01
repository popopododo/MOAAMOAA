package com.ssafy.moamoa.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.ssafy.moamoa.domain.ProfileSearchStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
public class Profile {
	@Id
	@GeneratedValue
	@Column(name = "profile_no")
	private Long id;

	@NotNull
	@MapsId
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_no")
	private User user;

	@NotNull
	@Column(name = "profile_nickname")
	private String nickname;

	@NotNull
	@ColumnDefault("'ALL'")
	@Enumerated(EnumType.STRING)
	private ProfileSearchStatus searchState = ProfileSearchStatus.ALL;

	@Column(name = "profile_img")
	private String img;

	@Lob
	@Column(name = "profile_context", columnDefinition = "TEXT")
	private String context;

	@OneToMany(mappedBy = "profile")
	private List<ProfileTechStack> techStacks = new ArrayList<>();

	@OneToMany(mappedBy = "profile")
	private List<ProfileArea> profileAreas = new ArrayList<>();

	public Profile() {
	}

	//==set==//
	public void setUser(User newUser) {
		this.user = newUser;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setSearchState(ProfileSearchStatus profileSearchStatus) {
		this.searchState = profileSearchStatus;
	}

}