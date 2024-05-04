package com.example.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;


@MappedSuperclass
public abstract class DeletedAtAbstractEntity extends BaseTimeAbstractEntity {

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

    @Column(name = "DELETION_YN")
    private String deletionYn;

    @Override
    protected void onCreate() {
        super.onCreate();
        deletionYn = "N";
    }

    protected void delete(){
        deletedAt = LocalDateTime.now();
        deletionYn = "Y";
    }

}