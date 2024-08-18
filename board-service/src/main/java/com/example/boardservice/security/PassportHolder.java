package com.example.boardservice.security;

import com.example.common.domain.Passport;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import lombok.SneakyThrows;
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

    @SneakyThrows
    public static String getPassportJson() {
        Passport passport = getPassport();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(passport);
    }

    public static void clear(){
        holder.remove();
    }

}
