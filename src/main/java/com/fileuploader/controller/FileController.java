package com.fileuploader.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.Date;
import java.util.List;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fileuploader.model.FileMetadata;
import com.fileuploader.service.FileService;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/files")
public class FileController {

   
	@Autowired
    private FileService fileService;

    @GetMapping(value = "/download")
    public ResponseEntity<byte[]> getFilesByFilters(@RequestParam(value = "author", required = false) String author, @RequestParam(value = "startDate", required = false) Long startDate, @RequestParam(value = "endDate", required = false) Long endDate, @RequestParam(value = "fileType", required = false) String fileType) {

        Date start = startDate != null ? new Date(startDate) : null;
        Date end = endDate != null ? new Date(endDate) : null;

        List<FileMetadata> files = fileService.getFilesByFilters(author, start, end, fileType);

        if (files.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipArchiveOutputStream zipOut = new ZipArchiveOutputStream(byteArrayOutputStream)) {

            for (FileMetadata fileMetadata : files) {
                ZipArchiveEntry zipEntry = new ZipArchiveEntry(fileMetadata.getId() + "." + fileMetadata.getFileType());
                zipOut.putArchiveEntry(zipEntry);

                zipOut.write(fileMetadata.getFileContent());
                zipOut.closeArchiveEntry();
            }

            zipOut.finish();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error creating ZIP file".getBytes());
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"files.zip\"").header(HttpHeaders.CONTENT_TYPE, "application/zip").body(byteArrayOutputStream.toByteArray());
    }


    @PostMapping(value = "/upload", consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file, @RequestParam("author") String author) {
        try {

            fileService.uploadFile(file, author, file.getContentType());
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }
}