package com.mandiri.blobExample.entity;

import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ms_upload")
public class FileUploader {

    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    private String id;

    @Lob
    @Column(name = "data")
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] data;

    private String fileName;
    private String fileType;

    public FileUploader(byte[] data, String fileName, String fileType) {
        this.data = data;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public FileUploader(){

    }
}
