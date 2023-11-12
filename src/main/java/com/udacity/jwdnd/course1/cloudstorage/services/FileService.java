package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    boolean isFileNameAvailable(String fileName, Integer userId);
    void uploadFile(MultipartFile file, Integer userId) throws IOException;
    List<File> getListFilesByUserId(Integer userId);
    File getFileById(Integer fileId);
    void deleteFile(Integer fileId);
}
