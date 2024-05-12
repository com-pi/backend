package com.example.boardservice.security;

import com.example.common.domain.Passport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class PassportFilter implements Filter {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        try {
            String passportJson = httpRequest.getHeader("passport");

            if(passportJson == null && isFromSwagger(httpRequest)) {
                filterChain.doFilter(servletRequest, servletResponse);
            }

            if(passportJson == null) {
                throw new RuntimeException("로그인이 필요합니다.");
            }

            Passport passport = objectMapper.readValue(passportJson, Passport.class);
            PassportHolder.setPassport(passport);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("신원을 인식하는데 실패했습니다.", e);
        } catch (Exception ignored){

        } finally {
            PassportHolder.clear();
        }

    }

    private Boolean isFromSwagger(HttpServletRequest request){
        String referer = request.getHeader("referer");
        String requestURI = request.getRequestURI();
        boolean isFromSwagger = referer != null && referer.contains("swagger");
        boolean isApiDocRequest = requestURI.endsWith("/v3/api-docs");
        return isApiDocRequest || isFromSwagger;
    }

}
