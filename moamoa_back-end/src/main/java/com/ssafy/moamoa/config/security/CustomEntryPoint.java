package com.ssafy.moamoa.config.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        log.warn("CustomEntryPoint : 잘못된 토큰으로 페이지 요청");

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, String> map = new HashMap<>();
        map.put("errortype", "Unauthorized");
        map.put("code", "401");
        map.put("message", "잘못된 토큰으로 접근하였습니다. 다시 로그인 해주세요");

        response.getWriter().write(objectMapper.writeValueAsString(map));
    }
}
