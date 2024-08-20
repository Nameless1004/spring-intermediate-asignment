package com.sparta.springintermediateasignment.user.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.springintermediateasignment.user.entity.User;
import com.sparta.springintermediateasignment.user.repository.UserRepository;
import com.sparta.springintermediateasignment.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.lang.model.type.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j(topic = "AuthFilter")
@Component
@Order(2)
@RequiredArgsConstructor
public class AuthFilter implements Filter {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String url = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(url) &&
            (url.startsWith("/api/users/login") || url.startsWith("/api/users/signup"))) {
            log.info("인증 처리를 하지 않는 URL: " + url);
            // 회원가입, 로그인 관련 API 는 인증 필요없이 요청 진행
            chain.doFilter(request, response); // 다음 Filter 로 이동
        } else {

            log.info("인증 처리 : " + url);
            // 나머지 API 요청은 인증 처리 진행
            // 토큰 확인

            // Header에서 검사
            String tokenValue = httpServletRequest.getHeader("Authorization");

            // String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);

            if (StringUtils.hasText(tokenValue)) { // 토큰이 존재하면 검증 시작
                // JWT 토큰 substring
                String token = jwtUtil.substringToken(tokenValue);

                ErrorInfo errorInfo = new ErrorInfo();

                // 토큰 검증
                if (!jwtUtil.validateToken(token, errorInfo)) {
                    jwtExceptionHandler((HttpServletResponse)response, errorInfo);
                    return;
                }

                // 토큰에서 사용자 정보 가져오기
                Claims info = jwtUtil.getUserInfoFromToken(token);

                User user = userRepository.findByName(info.getSubject())
                    .orElseThrow(() -> new NullPointerException("Not Found User"));

                request.setAttribute("user", user);
                chain.doFilter(request, response); // 다음 Filter 로 이동
            } else {
                jwtExceptionHandler((HttpServletResponse)response, new ErrorInfo("토큰이 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
            }
        }



    }

    public void jwtExceptionHandler(HttpServletResponse response, ErrorInfo errorInfo) {
        response.setStatus(errorInfo.getHttpStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            String json = new ObjectMapper().writeValueAsString(errorInfo);
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}