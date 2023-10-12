package site.thanhtungle.storageservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public FileDto uploadFile(@RequestPart("file") MultipartFile file,
                                                   @RequestPart("filePath") String filePath) {
        return storageService.uploadFile(file, filePath);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<FileDto> uploadFiles(@RequestParam("files") List<MultipartFile> fileList,
                                                    @RequestParam("filePaths") List<String> filePathList) {
        return storageService.uploadFiles(fileList, filePathList);
    }

    @GetMapping
    public Resource getFile(@RequestParam("filePath") String filePath) {
        return storageService.getFile(filePath);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFiles(@RequestParam("filePath") List<String> filePathList) {
        storageService.deleteFiles(filePathList);
    }
}
