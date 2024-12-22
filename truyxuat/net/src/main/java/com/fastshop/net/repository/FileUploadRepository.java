package com.fastshop.net.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastshop.net.model.FileUpload;

@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, Long>{
    public void deleteByFileCode(String fileCode);
    public FileUpload findByFileCode(String fileCode);
}
