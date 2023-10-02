package site.thanhtungle.storageservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.commons.model.dto.FileDto;
import site.thanhtungle.storageservice.service.StorageService;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@AllArgsConstructor
public class FileController {

    private final StorageService storageService;

    @PostMapping("/single")
    public ResponseEntity<FileDto> uploadFile(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("filePath") String filePath) {
        FileDto response = storageService.uploadFile(file, filePath);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<List<FileDto>> uploadFiles(@RequestParam("files") List<MultipartFile> fileList,
                                                    @RequestParam("filePaths") List<String> filePathList) {
        List<FileDto> response = storageService.uploadFiles(fileList, filePathList);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<Resource> getFile(@RequestParam("filePath") String filePath) {
        Resource content = storageService.getFile(filePath);
        return ResponseEntity.ok().body(content);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFiles(@RequestParam("filePath") List<String> filePathList) {
        storageService.deleteFiles(filePathList);
    }
}
