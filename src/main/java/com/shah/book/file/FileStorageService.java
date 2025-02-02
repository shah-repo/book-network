package com.shah.book.file;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;
import static java.lang.System.currentTimeMillis;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageService {

    @Value("${application.file.upload.file-output-path}")
    private String fileUploadPath;

    public String saveFile(@Nonnull MultipartFile sourceFile,
                           @Nonnull Integer userId) {
        final String subFilePath = "users" + separator + userId;

        return uploadFile(sourceFile, subFilePath);

    }

    private String uploadFile(
            @Nonnull MultipartFile sourceFile,
            @Nonnull String subFilePath
    ) {
        final String finalFilepath = fileUploadPath + separator + subFilePath;
        File targetFolder = new File(finalFilepath);
        if (!targetFolder.exists()) {
            boolean folderCreated = targetFolder.mkdirs();
            if (!folderCreated) {
                log.warn("Failed to create the target folder");
                return null;
            }
        }

        final String fileExtention = getFileExtention(sourceFile.getOriginalFilename());
        // eg: ./upload/users/1/23416517261872.jpg
        String targetFilePath = finalFilepath + separator + currentTimeMillis() + "." + fileExtention;
        Path targetPath = Paths.get(targetFilePath);
        try {
            Files.write(targetPath, sourceFile.getBytes());
            log.info("File saved to: " + targetFilePath);
            return targetFilePath;
        } catch (IOException e) {
            log.error("File was not saved", e);
        }
        return null;
    }

    private String getFileExtention(String filename) {
        if (filename == null || filename.isEmpty()) {
            return "";
        }
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }

        return filename.substring(lastDotIndex + 1).toLowerCase();
    }
}
