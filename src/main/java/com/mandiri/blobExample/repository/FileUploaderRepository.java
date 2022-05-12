package com.mandiri.blobExample.repository;

import com.mandiri.blobExample.entity.FileUploader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileUploaderRepository extends JpaRepository<FileUploader,String> {
}
