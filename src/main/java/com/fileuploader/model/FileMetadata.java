package com.fileuploader.model;



import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;


    private Date uploadDatetime;

    private String fileType;

    @Lob
    private byte[] fileContent;

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public Date getUploadDatetime() {
        return uploadDatetime;
    }

    public String getFileType() {
        return fileType;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setUploadDatetime(Date uploadDatetime) {
        this.uploadDatetime = uploadDatetime;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }


    
}