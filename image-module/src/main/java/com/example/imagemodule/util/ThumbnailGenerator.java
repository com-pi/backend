package com.example.imagemodule.util;

import com.example.imagemodule.domain.CustomMultipartFile;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ThumbnailGenerator {


    // Todo 에러 수정 (Thumbnail 안됨)

    public static MultipartFile createThumbnail(MultipartFile file) throws IOException {
        int quality = 100;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int size = outputStream.toByteArray().length;

        while (size > 30000L && quality > 0) {
            outputStream.reset();

            Thumbnails.of(file.getInputStream())
                    .scale(1.0)
                    .outputQuality(quality / 100.0)
                    .outputFormat("jpeg")
                    .toOutputStream(outputStream);

            quality -= 10;
        }

        return CustomMultipartFile.of(
                outputStream.toByteArray(),
                "thumbnail_" + file.getOriginalFilename(),
                "image/jpeg");
    }
}
