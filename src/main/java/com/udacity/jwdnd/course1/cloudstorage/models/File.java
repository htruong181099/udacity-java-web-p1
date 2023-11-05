package com.udacity.jwdnd.course1.cloudstorage.models;

public class File {
    private Integer fieldId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private Integer userId;
    private Object fileData;

    public File(){}
    public File(Integer fieldId, String fileName, String contentType, String fileSize, Integer userId, Object fileData) {
        this.fieldId = fieldId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userId = userId;
        this.fileData = fileData;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Object getFileData() {
        return fileData;
    }

    public void setFileData(Object fileData) {
        this.fileData = fileData;
    }
}
