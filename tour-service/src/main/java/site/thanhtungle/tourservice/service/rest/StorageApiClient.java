package site.thanhtungle.tourservice.service.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.commons.model.dto.FileDto;

import java.util.List;

@FeignClient(name = "STORAGE-SERVICE")
public interface StorageApiClient {

    @PostMapping(value = "/api/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<FileDto> uploadFiles(
            @RequestPart("files") List<MultipartFile> fileList,
            @RequestPart("filePaths") List<String> filePathList
    );
}
