package com.example.myplant.security;

import com.example.common.domain.Passport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class PassportFilter implements Filter{

    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        try {
            String passportJson = httpRequest.getHeader("passport");

            if (passportJson == null && isFromSwagger(httpRequest)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            PassportHolder.setPassport(Passport.anonymous());

            if (passportJson != null) {
                Passport passport = objectMapper.readValue(passportJson, Passport.class);
                PassportHolder.setPassport(passport);
            }

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("신원을 인식하는데 실패했습니다.", e);
        } finally {
            PassportHolder.clear();
        }
    }

    private Boolean isFromSwagger(HttpServletRequest request) {
        return request.getRequestURI().endsWith("/v3/api-docs");
    }
}

