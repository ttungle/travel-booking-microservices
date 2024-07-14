package site.thanhtungle.storageservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "BearerAuth")
public class FileController {

    private final StorageService storageService;

    @Operation(summary = "Upload single file")
    @PostMapping(value = "/single", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileDto uploadFile(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("filePath") String filePath) {
        return storageService.uploadFile(file, filePath);
    }

    @Operation(summary = "Upload multiple files")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<FileDto> uploadFiles(@RequestParam("files") List<MultipartFile> fileList,
                                                    @RequestParam("filePaths") List<String> filePathList) {
        return storageService.uploadFiles(fileList, filePathList);
    }

    @Operation(summary = "Get a file")
    @GetMapping
    public Resource getFile(@RequestParam("filePath") String filePath) {
        return storageService.getFile(filePath);
    }

    /**
     *
     * @param filePathList List of path to objects.
    * */
    @Operation(summary = "Delete multiple files")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFiles(@RequestParam("filePaths") List<String> filePathList) {
        storageService.deleteFiles(filePathList);
    }

    /**
     *
     * @param folderPath The folder which contains objects
    * */
    @Operation(summary = "Delete a folder")
    @DeleteMapping("/folder")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFolder(@RequestParam("folderPath") String folderPath) {
        storageService.deleteFolder(folderPath);
    }
}
