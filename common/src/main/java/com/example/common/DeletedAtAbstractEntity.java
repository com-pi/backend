package com.example.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;


@MappedSuperclass
public class DeletedAtAbstractEntity {

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

    @Column(name = "DELETION_YN")
    private String deletionYn;

    @PrePersist
    public void onCreate() {
        this.deletionYn = "Y";
    }

    protected void delete(){
        deletedAt = LocalDateTime.now();
        deletionYn = "Y";
    }

}