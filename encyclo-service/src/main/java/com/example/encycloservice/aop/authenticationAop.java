package com.example.encycloservice.aop;

import com.example.common.annotation.Authenticate;
import com.example.common.domain.Passport;
import com.example.common.domain.Role;
import com.example.common.exception.CommonException;
import com.example.encycloservice.aop.filter.PassportHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class authenticationAop {

    @Before("@annotation(com.example.common.annotation.Authenticate)")
    public void beforeAuthenticateMethod(JoinPoint joinPoint) {
        Passport passport = PassportHolder.getPassport();
        if(passport != null){
            Role permission = getPermission(joinPoint);
            Role role = passport.role();

            if(!role.hasPermission(permission)){
                if(role.equals(Role.ANONYMOUS)){
                    throw new CommonException("로그인이 필요한 요청입니다.", HttpStatus.UNAUTHORIZED.value());
                } else {
                    throw new CommonException("접근 권한이 없습니다.", HttpStatus.FORBIDDEN.value());
                }
            }
        }
    }

    private Role getPermission(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Authenticate authenticate = method.getAnnotation(Authenticate.class);
        return authenticate.value();
    }
}
