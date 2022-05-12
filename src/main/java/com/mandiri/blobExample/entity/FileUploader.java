package com.mandiri.blobExample.entity;

import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "ms_upload")
public class FileUploader {

    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    private String id;

    @Lob
    @Column(name = "data", columnDefinition="BLOB")
    private byte[] data;

    private String fileName;
    private String fileType;

    public FileUploader() {
    }

    public FileUploader(byte[] data, String fileName, String fileType) {
        this.data = data;
        this.fileName = fileName;
        this.fileType = fileType;
    }
}
