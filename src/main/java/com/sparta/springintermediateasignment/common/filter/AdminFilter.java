package com.sparta.springintermediateasignment.common.filter;

import com.sparta.springintermediateasignment.common.util.JwtUtil;
import com.sparta.springintermediateasignment.user.enums.UserRole;
import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j(topic = "AdminFilter")
@Component
@Order(2)
@RequiredArgsConstructor
public class AdminFilter implements Filter {

    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(url) && url.startsWith("/api/schedules/")) {
            String tokenValue = httpServletRequest.getHeader("Authorization");
            String token = jwtUtil.substringToken(tokenValue);

            String method = httpServletRequest.getMethod();
            Claims info = jwtUtil.getUserInfoFromToken(token);
            String role = (String) info.get(JwtUtil.AUTHORIZATION_KEY);
            // 일정의 삭제 수정은 어드민만 할 수 있다.
            // 권한이 유저이면
            log.info(role);

            if ((method.equals("DELETE") || method.equals("PATCH")) && role.equals(
                UserRole.USER.getAuthority())) {
                jwtUtil.jwtExceptionHandle((HttpServletResponse) response,
                    new ErrorInfo("권한이 없습니다.", HttpStatus.FORBIDDEN), log);
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
