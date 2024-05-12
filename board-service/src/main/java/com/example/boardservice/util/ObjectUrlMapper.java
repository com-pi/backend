package com.example.boardservice.util;

import java.util.List;


public interface ObjectUrlMapper {

    String toObject(String url);
    String toUrl(String object);
    List<String> toUrl(List<String> objectNames);

}
