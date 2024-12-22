package com.fastshop.net.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fastshop.net.service.FileUploadService;

import java.io.IOException;
@RestController
@RequestMapping("/rest/files")
public class RestFileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    @GetMapping("/preview/{fileCode}")
    public ResponseEntity<byte[]> previewImage(@PathVariable("fileCode") String fileCode) throws IOException {
        Resource resource = null;
        String contentType = null;
        try {
            resource = fileUploadService.getFileAsResource(fileCode);
            contentType = resource.getURL().openConnection().getContentType();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        byte[] imageData = StreamUtils.copyToByteArray(resource.getInputStream());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .cacheControl(CacheControl.noCache().mustRevalidate())
                .body(imageData);
    }
}
