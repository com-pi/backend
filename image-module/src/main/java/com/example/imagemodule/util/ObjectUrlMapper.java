package com.example.imagemodule.util;

import com.example.imagemodule.domain.MinioBucket;

import java.util.List;


public interface ObjectUrlMapper {

    String toObject(String url);
    String toUrl(String object, MinioBucket bucket);
    List<String> toUrl(List<String> objectNames, MinioBucket bucket);

}
