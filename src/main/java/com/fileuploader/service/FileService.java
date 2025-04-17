package com.fileuploader.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fileuploader.model.FileMetadata;
import com.fileuploader.repository.FileMetadataRepository;

@Service
public class FileService {

    @Autowired
    private FileMetadataRepository repository;

    public FileMetadata uploadFile(MultipartFile file, String author, String fileType) throws IOException {
        FileMetadata metadata = new FileMetadata();
        metadata.setAuthor(author);
        metadata.setUploadDatetime(new java.util.Date());
        metadata.setFileType(fileType);
        metadata.setFileContent(file.getBytes());
        return repository.save(metadata);
    }

    public List<FileMetadata> getFilesByFilters(String author, Date startDate, Date endDate, String fileType) {
        if (author != null && startDate != null && endDate != null && fileType != null) {
            return repository.findByAuthorAndUploadDatetimeBetweenAndFileType(author, startDate, endDate, fileType);
        }

        if (author != null && fileType != null) {
            return repository.findByAuthorAndFileType(author, fileType);
        }

        if (author != null && startDate != null && endDate != null) {
            return repository.findByAuthorAndUploadDatetimeBetween(author, startDate, endDate);
        }

        if (startDate != null && endDate != null && fileType != null) {
            return repository.findByUploadDatetimeBetweenAndFileType(startDate, endDate, fileType);
        }

        if (author != null) {
            return repository.findByAuthor(author);
        }

        if (startDate != null && endDate != null) {
            return repository.findByUploadDatetimeBetween(startDate, endDate);
        }

        if (fileType != null) {
            return repository.findByFileType(fileType);
        }

        return repository.findAll();
    }
}