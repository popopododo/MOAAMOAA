package com.ssafy.moamoa.service;

import java.util.List;
import java.util.Optional;

import com.ssafy.moamoa.config.security.JwtTokenProvider;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.moamoa.domain.Profile;
import com.ssafy.moamoa.domain.ProfileSearchStatus;
import com.ssafy.moamoa.domain.User;
import com.ssafy.moamoa.exception.DuplicateProfileNicknameException;
import com.ssafy.moamoa.exception.DuplicateUserEmailException;
import com.ssafy.moamoa.repository.ProfileRepository;
import com.ssafy.moamoa.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    // 회원 한명 조회
    public Optional<User> findUser(Long userId) {
        return userRepository.findById(userId);
    }

    // 회원 전체 조회
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    // 이메일 중복 조회
    public void validateDuplicateUserEmail(User user) {
        Optional<User> findUsers = userRepository.findByEmail(user.getEmail());
        if (!findUsers.isEmpty()) {
            throw new DuplicateUserEmailException("이미 존재하는 회원입니다.");
        }
    }

    // 닉네임 중복 조회
    public void validateDuplicateProfileNickname(Profile profile) {
        List<Profile> findProfiles = profileRepository.findByNickname(profile.getNickname());
        if (!findProfiles.isEmpty()) {
            throw new DuplicateProfileNicknameException("이미 존재하는 닉네임입니다.");
        }
    }

    // 회원 가입
    public String signup(String email, String password, String nickname) {
        // user
        User user = User.builder().email(email).password(getEncodedPassword(password)).build();
        // profile
        Profile profile = Profile.builder().nickname(nickname).searchState(ProfileSearchStatus.ALL).build();

        user.setProfile(profile);

        validateDuplicateUserEmail(user);
        validateDuplicateProfileNickname(profile);

        userRepository.save(user);
        profileRepository.save(profile);
        return nickname;
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public String authenticateUser(String username, String password) {
        try {
            //UsernamePasswordAuthenticationToken 생성
            //DaoAuthenticationProvider userDetailsService 에서 UserDetails 조회
            //passwordEncoder 로 비밀번호 검증
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                    password);
            authenticationManager.authenticate(authenticationToken); //

            //검증 완료
            //Security Context Holder 에 넣기
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            //토큰 생성 및 쿠키에 넣어서 리턴
            User user = userRepository.findByEmail(username).get();
            Profile profile = profileRepository.findByUser_Id(user.getId()).get(0);

            return jwtTokenProvider.createAccessToken(username, profile.getNickname());
        } catch (AuthenticationException e) {
            //검증 실패
            //AuthenticationEntryPoint 실행
            return null;

            // throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (AccessDeniedException e) {
            //AccessDeniedHandler
            return null;
        }
    }
}
