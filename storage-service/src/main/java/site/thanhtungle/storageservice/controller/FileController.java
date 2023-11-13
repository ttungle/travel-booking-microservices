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

    @PostMapping(value = "/single", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileDto uploadFile(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("filePath") String filePath) {
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

    /**
     *
     * @param filePathList List of path to objects.
    * */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFiles(@RequestParam("filePaths") List<String> filePathList) {
        storageService.deleteFiles(filePathList);
    }

    /**
     *
     * @param folderPath The folder which contains objects
    * */
    @DeleteMapping("/folder")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFolder(@RequestParam("folderPath") String folderPath) {
        storageService.deleteFolder(folderPath);
    }
}
