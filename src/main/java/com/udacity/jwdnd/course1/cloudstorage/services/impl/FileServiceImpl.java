package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    private final FileMapper fileMapper;

    public FileServiceImpl(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @Override
    public boolean isFileNameAvailable(String fileName, Integer userId) {
        return fileMapper.findFileByFilename(fileName, userId) == null;
    }

    @Override
    public void uploadFile(MultipartFile file, Integer userId) throws IOException {
        File newFile = new File();
        newFile.setFileName(file.getOriginalFilename());
        newFile.setFileSize(file.getSize());
        newFile.setContentType(file.getContentType());
        newFile.setFileData(file.getBytes());
        newFile.setUserId(userId);

        try {
            fileMapper.insert(newFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<File> getListFilesByUserId(Integer userId) {
        return fileMapper.findFilesByUserId(userId);
    }

    @Override
    public File getFileById(Integer fileId) {
        return fileMapper.findFileById(fileId);
    }

    @Override
    public void deleteFile(Integer fileId) {
        fileMapper.delete(fileId);
    }
}
