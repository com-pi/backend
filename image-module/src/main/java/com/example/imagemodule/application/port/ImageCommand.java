package com.example.imagemodule.application.port;

import com.example.imagemodule.domain.ImageAndThumbnail;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageCommand {

    List<String> saveImages(SaveImagesCommand images);
    ImageAndThumbnail saveProfileImage(MultipartFile image);

}
