package com.fastshop.net.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fastshop.net.model.FileUpload;
import com.fastshop.net.repository.FileUploadRepository;
import com.fastshop.net.service.FileUploadService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class FileUploadServiceImpl implements FileUploadService {
    @Autowired
    private FileUploadRepository fileUploadRepository;
    private Path foundFile;

    @Override
    public FileUpload uploadFile(MultipartFile multipartFile) throws IOException {
        Path uploadDirectory = Paths.get("Files-Upload");

        if (!Files.exists(uploadDirectory)) {
            Files.createDirectories(uploadDirectory);
        }

        String fileCode = UUID.randomUUID().toString();
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadDirectory.resolve(fileCode);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Error saving file: ", e);
        }

        FileUpload fileUpload = new FileUpload();

        // Extract file extension
        String originalFileName = multipartFile.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        fileUpload.setFileName(StringUtils.cleanPath(multipartFile.getOriginalFilename()));
        fileUpload.setFileType(fileExtension);
        fileUpload.setFileCode(fileCode);
        fileUpload.setSize(multipartFile.getSize());
        return fileUploadRepository.save(fileUpload);
    }

    @Override
    public Resource getFileAsResource(String fileCode) throws IOException {
        Path uploadDirectory = Paths.get("Files-Upload");

        // Tìm file đầu tiên có tên bắt đầu bằng fileCode
        Optional<Path> foundFile = Files.list(uploadDirectory)
                .filter(file -> file.getFileName().toString().startsWith(fileCode))
                .findFirst();

        if (foundFile.isPresent()) {
            return new UrlResource(foundFile.get().toUri());
        }

        throw new IOException("File not found with fileCode: " + fileCode);
    }

    @Override
    public void deleteFileByFileCode(String fileCode) throws IOException {
        FileUpload fileUpload = fileUploadRepository.findByFileCode(fileCode);
        fileUploadRepository.delete(fileUpload);
    }

    @Override
    public FileUpload getFileUploadByFileCode(String fileCode) {
        return fileUploadRepository.findByFileCode(fileCode);
    }
}
