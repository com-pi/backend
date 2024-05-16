package com.example.myplant.application.port.out;

import java.util.List;

public interface ImageUploadPort {
    List<String> uploadImages(List<String> imagePaths);
}
