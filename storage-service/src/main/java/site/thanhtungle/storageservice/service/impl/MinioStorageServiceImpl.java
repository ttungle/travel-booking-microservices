package site.thanhtungle.storageservice.service.impl;

import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.storageservice.service.MinioStorageService;

import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
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

    @Override
    public void removeObject(String filePath) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filePath)
                    .build()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void removeObjects(List<String> filePathList) {
        if (CollectionUtils.isEmpty(filePathList)) return;
        List<DeleteObject> objects = filePathList.stream().map(DeleteObject::new).toList();

       try {
           Iterable<Result<DeleteError>> results = minioClient.removeObjects(
                   RemoveObjectsArgs
                   .builder()
                   .bucket(bucketName)
                   .objects(objects)
                   .build()
           );

           for (Result<DeleteError> result : results) {
               result.get();
           }
       } catch (Exception e) {
           log.error(e.getMessage());
           throw new RuntimeException(e.getMessage());
       }
    }
}
