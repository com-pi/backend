package com.example.myplant.util;

import  java.util.List;

public interface ObjectUrlMapper {

    String toObject(String url);
    String toUrl(String object);
    List<String> toUrl(List<String> objectNames);
}
