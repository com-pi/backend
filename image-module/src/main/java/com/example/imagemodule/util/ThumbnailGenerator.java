package com.example.imagemodule.util;

import com.example.imagemodule.domain.CustomMultipartFile;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ThumbnailGenerator {

    public static MultipartFile createThumbnail(MultipartFile file) throws IOException {
        int quality = 100;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        while (true) {
            outputStream.reset();

            Thumbnails.of(file.getInputStream())
                    .scale(1.0)
                    .outputQuality(quality / 100.0)
                    .outputFormat("jpeg")
                    .toOutputStream(outputStream);

            int size = outputStream.size();
            if (size <= 30000L || quality <= 10) {
                break;
            }
            quality -= 10;
        }

        return CustomMultipartFile.of(
                outputStream.toByteArray(),
                "thumbnail_" + file.getOriginalFilename(),
                "image/jpeg");
    }

}
