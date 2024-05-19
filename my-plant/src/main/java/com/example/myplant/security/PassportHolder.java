package com.example.myplant.security;

import com.example.common.domain.Passport;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

@Component
public class PassportHolder {

    private static final ThreadLocal<Passport> holder = new ThreadLocal<>();

    public static void setPassport(Passport passport) {
        holder.set(passport);
    }

    @Nonnull
    public static Passport getPassport() {
        return holder.get();
    }

    public static void clear(){
        holder.remove();
    }

}
