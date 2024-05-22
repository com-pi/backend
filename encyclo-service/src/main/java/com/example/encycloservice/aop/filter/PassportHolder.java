package com.example.encycloservice.aop.filter;

import com.example.common.domain.Passport;
import org.springframework.stereotype.Component;

@Component
public class PassportHolder {

    private static final ThreadLocal<Passport> holder = new ThreadLocal<>();

    public static void setPassport(Passport passport) {
        holder.set(passport);
    }

    public static Passport getPassport() {
        return holder.get();
    }

    public static void clear(){
        holder.remove();
    }

}
