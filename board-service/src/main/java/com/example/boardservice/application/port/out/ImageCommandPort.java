package com.example.boardservice.application.port.out;

import java.util.List;

public interface ImageCommandPort {

    List<String> saveImages(SaveImagesCommand images);

}
