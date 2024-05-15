package com.example.imagemodule.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Getter
@RequiredArgsConstructor
public class CustomMultipartFile implements MultipartFile {

    private final byte[] input;
    private final String originalFileName;
    private final String contentType;

    public static CustomMultipartFile of(@NotNull byte[] input, @NotNull String originalFileName, @NotNull String contentType) {
        return new CustomMultipartFile(input, originalFileName, contentType);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getOriginalFilename() {
        return originalFileName;
    }

    @Override
    public String getContentType() {
        return "";
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @NotNull
    @Override
    public byte[] getBytes() {
        return input;
    }

    @NotNull
    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(input);
    }

    @Override
    public void transferTo(@NotNull File destination) throws IOException, IllegalStateException {
        try(FileOutputStream fos = new FileOutputStream(destination)) {
            fos.write(input);
        }
    }
}
