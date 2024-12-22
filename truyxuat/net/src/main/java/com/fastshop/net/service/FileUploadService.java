package com.fastshop.net.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.fastshop.net.model.FileUpload;

import java.io.IOException;

public interface FileUploadService {
    FileUpload uploadFile(MultipartFile multipartFile) throws IOException;
    Resource getFileAsResource(String fileCode) throws IOException;
    void deleteFileByFileCode(String fileCode) throws IOException;
    FileUpload getFileUploadByFileCode(String fileCode);
}
