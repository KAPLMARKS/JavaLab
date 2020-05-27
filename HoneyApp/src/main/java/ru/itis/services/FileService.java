package ru.itis.services;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.models.FileInfo;

import javax.servlet.http.HttpServletResponse;

public interface FileService {
    FileInfo saveFile(MultipartFile file, Long id);
    void writeFileToResponse(String fileName, HttpServletResponse response);
}