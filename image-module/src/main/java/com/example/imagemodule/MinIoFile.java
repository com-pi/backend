package com.example.imagemodule;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MinIoFile {

    private String fileName;
    private MinioBucket bucket;

    public static MinIoFile of(String fileName, MinioBucket bucket) {
        return new MinIoFile(fileName, bucket);
    }

}
