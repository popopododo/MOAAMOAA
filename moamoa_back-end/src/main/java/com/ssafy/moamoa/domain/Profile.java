package com.ssafy.moamoa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.Getter;

@Entity
@Getter
@DynamicInsert
public class Profile {
	@Id
	@Column(name = "profile_no")
	private Long id;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_no")
	private User user;

	@NotNull
	@Column(name = "profile_nickname")
	private String nickname;

	@NotNull
	@ColumnDefault("'ALL'")
	@Enumerated(EnumType.STRING)
	private ProfileSearchStatus searchState;

	@Column(name = "profile_img")
	private String img;

	@Lob
	@Column(name = "profile_context")
	private String context;

}
