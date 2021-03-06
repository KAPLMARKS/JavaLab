package ru.itis.services;

import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.models.FileInfo;
import ru.itis.repositories.FilesRepository;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Component
public class FileServiceImpl implements FileService {
    @Autowired
    private FilesRepository filesRepository;

    @Value("${storage.path}")
    private String path;

    @SneakyThrows
    @Override
    public FileInfo saveFile(MultipartFile file, Long id) {
        String storageName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        FileInfo fileInfo = FileInfo.builder()
                .originalFileName(file.getOriginalFilename())
                .type(file.getContentType())
                .storageFileName(storageName)
                .size(file.getSize())
                .url(path + storageName)
                .userId(id)
                .build();
        filesRepository.save(fileInfo);
        Files.copy(file.getInputStream(), Paths.get(path, storageName));
        return fileInfo;
    }

    @SneakyThrows
    @Override
    public void writeFileToResponse(String fileName, HttpServletResponse response) {
        Optional<FileInfo> optionalFileInfo = filesRepository.findByStorageName(fileName);
        if(!optionalFileInfo.isPresent()) {
            System.err.println("File not found");
            return;
        }
        FileInfo file = optionalFileInfo.get();
        response.setContentType(file.getType());
        InputStream inputStream = new FileInputStream(new java.io.File(file.getUrl()));
        org.apache.commons.io.IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
    }
}