package com.stankin.lab6.responses;

public class SuccessResponse extends Response {
    private final String fileName;
    private final Long fileId;

    public SuccessResponse(String message, Long fileId, String fileName) {
        this.message = message;
        this.fileName = fileName;
        this.fileId = fileId;
    }

    public Long getFileId() {
        return fileId;
    }

    public String getFileName() {
        return fileName;
    }
}
