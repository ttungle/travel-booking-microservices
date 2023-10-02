package site.thanhtungle.storageservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;
import site.thanhtungle.commons.model.dto.FileDto;
import site.thanhtungle.storageservice.service.MinioStorageService;
import site.thanhtungle.storageservice.service.StorageService;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageServiceImpl implements StorageService {

    private final MinioStorageService minioStorageService;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @Value("${minio.url}")
    private String minioUrl;

    @Override
    public FileDto uploadFile(MultipartFile file, String filePath) {
        try {
            minioStorageService.save(file, filePath);

            return FileDto.builder()
                    .fileName(file.getOriginalFilename())
                    .description(file.getContentType())
                    .size(file.getSize())
                    .url(getPublicFileUrl(filePath))
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<FileDto> uploadFiles(List<MultipartFile> fileList, List<String> filePathList) {
        return IntStream.range(0, fileList.size()).mapToObj(index -> {
            try {
                MultipartFile file = fileList.get(index);
                String filePath = filePathList.get(index);
                minioStorageService.save(file, filePath);

                return FileDto.builder()
                        .fileName(file.getOriginalFilename())
                        .description(file.getContentType())
                        .size(file.getSize())
                        .url(getPublicFileUrl(filePath))
                        .build();
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        }).toList();
    }

    @Override
    public Resource getFile(String filePath) {
        try {
            InputStream inputStream = minioStorageService.getInputStream(filePath);
            ByteArrayOutputStream content = new ByteArrayOutputStream();
            inputStream.transferTo(content);
            inputStream.close();
            return new ByteArrayResource(content.toByteArray());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteFile(String filePath) {
        minioStorageService.removeObject(filePath);
    }

    @Override
    public void deleteFiles(List<String> filePathList) {
        minioStorageService.removeObjects(filePathList);
    }

    @Override
    public String getPublicFileUrl(String filePath) {
        String path = String.format("%s/%s/%s", minioUrl, bucketName, filePath);
        return UriUtils.encodePath(path, StandardCharsets.UTF_8);
    }
}
