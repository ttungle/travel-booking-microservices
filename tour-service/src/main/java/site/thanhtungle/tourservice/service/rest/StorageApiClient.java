package site.thanhtungle.tourservice.service.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.commons.model.dto.FileDto;

import java.util.List;

@FeignClient(name = "STORAGE-SERVICE", path = "/api/files")
public interface StorageApiClient {

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<FileDto> uploadFiles(
            @RequestPart("files") List<MultipartFile> fileList,
            @RequestPart("filePaths") List<String> filePathList
    );

    @PostMapping(value = "/single", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    FileDto uploadFile(@RequestPart("file") MultipartFile file, @RequestPart("filePath") String filePath);

    @DeleteMapping(value = "/folder")
    void deleteFolder(@RequestParam("folderPath") String folderPath);
}
