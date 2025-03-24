package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.*;

public class FileUploadUtil {

    public static void deleteUploadedFile(String filePath) throws IOException {
        if (filePath == null || filePath.isEmpty()) return;
        Path path = Paths.get(filePath);
        Files.deleteIfExists(path);
    }
}

