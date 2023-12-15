package com.stankin.lab6;

import com.stankin.lab6.models.File;
import com.stankin.lab6.models.User;
import com.stankin.lab6.repositories.FileRepository;
import com.stankin.lab6.repositories.UserRepository;
import com.stankin.lab6.responses.ErrorResponse;
import com.stankin.lab6.responses.FileListResponse;
import com.stankin.lab6.responses.Response;
import com.stankin.lab6.responses.SuccessResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Objects;

@RestController
public class UploadController {
    private final FileRepository fileRepository;

    private final UserRepository userRepository;

    public UploadController(FileRepository fileRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/addFile")
    public ResponseEntity<Response> onFileUpload (
        @RequestParam("file") MultipartFile file
    ) {
        try {
            String fileName = file.getOriginalFilename();

            byte[] fileContent = file.getBytes();

            User user = this.userRepository.getCurrentUser();

            File newFile = new File(fileName, user);

            this.fileRepository.save(newFile);
            newFile.saveToDisk(fileContent);

            return ResponseEntity.ok().body(new SuccessResponse("Success", newFile.getId(), fileName));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Upload failed"));
        }
    }

    @GetMapping("/getFileById")
    public ResponseEntity<?> onFileRequested (
        @RequestParam("file_id") Integer fileId
    ) throws IOException {
        try {
            File file = this.fileRepository.getReferenceById(fileId.longValue());

            HttpHeaders headers = new HttpHeaders();

            byte[] fileContent = file.getFileContent();

            String contentType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(fileContent));

            if (contentType != null) {
                headers.setContentType(MediaType.parseMediaType(contentType));
            }

            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (jakarta.persistence.EntityNotFoundException ignored) {
            return ResponseEntity.status(404).body(new ErrorResponse("File not found"));
        }
    }

    @GetMapping("/getFilesList")
    public ResponseEntity<Response> onFileListRequested () {
        ArrayList<File> items = (ArrayList<File>) this.fileRepository.findAllByUserId(this.userRepository.getCurrentUser().getId());

        return ResponseEntity.status(200).body(new FileListResponse(items));
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<Response> onFileDeletionRequested (
        @RequestParam("file_id") Integer fileId
    ) {
        try {
            File file = this.fileRepository.getReferenceById(fileId.longValue());

            if (!Objects.equals(file.getOwnerId(), this.userRepository.getCurrentUser().getId())) {
                throw new Exception("Access denied");
            }

            this.fileRepository.delete(file);

            return ResponseEntity.status(200).body(new Response("File deleted"));
        } catch (jakarta.persistence.EntityNotFoundException ignored) {
            return ResponseEntity.status(404).body(new ErrorResponse("File not found"));
        } catch (Exception e) {
            return ResponseEntity.status(403).body(new ErrorResponse("Access denied"));
        }
    }
}
