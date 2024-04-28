package com.example.boardservice.domain;

public enum ArticleStatusType {

    ongoing, completed, saved;

    public static ArticleStatusType of(String status) {
        for(ArticleStatusType type : ArticleStatusType.values()){
            if(type.name().equalsIgnoreCase(status)) {
                return type;
            }
        }
        return null;
    }

}
