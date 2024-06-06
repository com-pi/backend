package com.example.authserver.aop.filter;

import com.example.common.domain.Passport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class PassportFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) {

        try {
            String passportJson = request.getHeader("passport");

            if(passportJson == null) {
                PassportHolder.setPassport(Passport.anonymous());
                filterChain.doFilter(request, response);
                return;
            }

            Passport passport = objectMapper.readValue(passportJson, Passport.class);
            PassportHolder.setPassport(passport);
            filterChain.doFilter(request, response);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("신원을 인식하는데 실패했습니다.", e);
        } catch (Exception ignored){
        } finally {
            PassportHolder.clear();
        }
    }

}
