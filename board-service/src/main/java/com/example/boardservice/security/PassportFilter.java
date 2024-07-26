package com.example.boardservice.security;

import com.example.boardservice.application.MemberService;
import com.example.common.domain.Passport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PassportFilter implements Filter {

    private final ObjectMapper objectMapper;
    private final MemberService memberService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        try {
            String passportJson = httpRequest.getHeader("passport");

            log.info("passportJson: {}", passportJson);
            log.info("isFromSwagger: {}", isFromSwagger(httpRequest));
            if(passportJson == null && isFromSwagger(httpRequest)) {
                filterChain.doFilter(servletRequest, servletResponse);
            }

            PassportHolder.setPassport(Passport.anonymous());

            if( passportJson != null ) {
                Passport passport = objectMapper.readValue(passportJson, Passport.class);
                memberService.integrateMember(passport);
                PassportHolder.setPassport(passport);
            }

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("신원을 인식하는데 실패했습니다.", e);
        } catch (Exception ignored){

        } finally {
            PassportHolder.clear();
        }

    }

    private Boolean isFromSwagger(HttpServletRequest request){
        log.info("request uri: {}", request.getRequestURI());
        return  request.getRequestURI().endsWith("/v3/api-docs");
    }

}
