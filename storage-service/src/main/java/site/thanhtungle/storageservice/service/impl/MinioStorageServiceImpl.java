package site.thanhtungle.storageservice.service.impl;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.storageservice.service.MinioStorageService;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class MinioStorageServiceImpl implements MinioStorageService {

    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @Value("${minio.part.size}")
    private long partSize;

    @Override
    public void save(MultipartFile file, String filePath) throws Exception {
        minioClient.putObject(
                PutObjectArgs
                .builder()
                .bucket(bucketName)
                .object(filePath)
                .stream(file.getInputStream(), file.getSize(), partSize)
                .build()
        );
    }

    @Override
    public InputStream getInputStream(String filePath) throws Exception {
        return minioClient.getObject(
                GetObjectArgs
                .builder()
                .bucket(bucketName)
                .object(filePath)
                .build()
        );
    }
}
