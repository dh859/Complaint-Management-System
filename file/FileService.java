package com.cms.cmsapp.file;

import com.cms.cmsapp.common.exceptions.FileUploadException;
import com.cms.cmsapp.common.exceptions.ResourceNotFoundException;
import com.cms.cmsapp.complaint.entity.Attatchment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileService {

    @Value("${file.upload-dir}")
    private String path;

    @Autowired
    private FileRepo fileRepo;

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        return (dotIndex > 0) ? fileName.substring(dotIndex) : "";
    }

    public Attatchment getFileById(Long fileId) {
        return fileRepo.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File Not Found By Id :" + fileId));
    }

    public Attatchment saveFile(MultipartFile file) {
        String fileName = UUID.randomUUID() + getFileExtension(file.getOriginalFilename());
        String filePath = path + File.separator + fileName;

        File directory = new File(path);
        if (!directory.exists())
            directory.mkdirs();

        try {
            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileUploadException("Error uploading file: " + e.getMessage());
        }

        return new Attatchment(fileName, file.getContentType(), filePath, file.getSize());
    }

    public File getFile(String filePath) {
        File file = new File(filePath);

        if (!file.isAbsolute()) {
            file = new File(path, filePath);
        }

        if (!file.exists()) {
            throw new ResourceNotFoundException(
                    "File not found at: " + file.getAbsolutePath());
        }

        return file;
    }

    public List<Attatchment> saveFiles(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            return Collections.emptyList();
        }
        return files.stream()
                .filter(file -> !file.isEmpty())
                .map(this::saveFile)
                .collect(Collectors.toList());
    }

    public List<File> getFile(String[] filePaths) {
        return filePaths == null ? Collections.emptyList()
                : Arrays.stream(filePaths)
                        .map(this::getFile)
                        .collect(Collectors.toList());
    }

    public void deleteFile(String filePath) {
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            throw new FileUploadException("Error deleting file: " + e.getMessage());
        }
    }

    public void deleteFiles(String[] filePaths) {
        for (String filePath : filePaths)
            deleteFile(filePath);
    }

    public byte[] readFileBytes(File file) {
        try {

            if (!file.exists()) {
                throw new FileUploadException("File not found by name: " + file.getName());
            }
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new FileUploadException("Error reading file: " + e.getMessage());
        }
    }

}
