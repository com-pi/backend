package com.example.boardservice.security;

import com.example.common.annotation.Authenticate;
import com.example.common.domain.Passport;
import com.example.common.domain.Role;
import com.example.common.exception.UnauthorizedException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class authenticationAop {

    @Pointcut("@annotation(com.example.common.annotation.Authenticate)")
    public void authenticatePointcut() {
        // 포인트컷 메소드의 본문은 비워둘 수 있습니다.
    }

    // 포인트컷 전에 실행될 어드바이스
    @Before("authenticatePointcut()")
    public void beforeAuthenticateMethod(JoinPoint joinPoint) {
        Passport passport = PassportHolder.getPassport();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Authenticate authenticate = method.getAnnotation(Authenticate.class);

        Role permission = authenticate.value();
        Role role = passport.role();
        if(!role.hasPermission(permission)){
            if(role.equals(Role.ANONYMOUS)){
                throw new UnauthorizedException("로그인이 필요한 요청입니다.");
            } else {
                throw new UnauthorizedException("접근 권한이 없습니다.");
            }
        }
    }

}
