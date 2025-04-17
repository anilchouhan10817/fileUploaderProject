package com.fileuploader.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fileuploader.model.FileMetadata;

@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {

    List<FileMetadata> findByAuthor(String author);

    List<FileMetadata> findByUploadDatetimeBetween(Date startDate, Date endDate);

    List<FileMetadata> findByFileType(String fileType);

    List<FileMetadata> findByAuthorAndUploadDatetimeBetweenAndFileType(String author, Date startDate, Date endDate, String fileType);

    List<FileMetadata> findByAuthorAndFileType(String author, String fileType);

    List<FileMetadata> findByAuthorAndUploadDatetimeBetween(String author, Date startDate, Date endDate);

    List<FileMetadata> findByUploadDatetimeBetweenAndFileType(Date startDate, Date endDate, String fileType);

    List<FileMetadata> findAll();
}