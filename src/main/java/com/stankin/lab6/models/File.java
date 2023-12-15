package com.stankin.lab6.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private LocalDateTime uploadDate;

    @JsonIgnore
    private String filePath;

    @ManyToOne
    private User user;

    public File() {
    }

    public Long getId () {
        return id;
    }

    @JsonIgnore
    public byte[] getFileContent () {
        try {
            Path path = Paths.get("./files/" + filePath);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            return null;
        }
    }

    public File (String fileName, User user) throws UnsupportedEncodingException {
        this.fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        this.filePath = user.getId().toString() + '/' + fileName;
        this.user = user;

        this.uploadDate = LocalDateTime.now();
    }

    public String getFileName () {
        return fileName;
    }

    public Long getOwnerId() {
        return user.getId();
    }

    public LocalDateTime getUploadDate () {
        return uploadDate;
    }

    @JsonIgnore
    public void saveToDisk(byte[] content) {
        try {
            Path userDirectory = Paths.get("./files/" + user.getId());

            if (!Files.exists(userDirectory)) {
                Files.createDirectories(userDirectory);
            }

            Path filePath = userDirectory.resolve(fileName);

            Files.write(filePath, content);
        } catch (IOException ignored) {
        }
    }
}
