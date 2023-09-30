package site.thanhtungle.storageservice.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MinioConfig {

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.username}")
    private String minioUsername;

    @Value("${minio.password}")
    private String minioPassword;

    @Value("${minio.bucket.name}")
    private String minioBucket;

    @Bean
    public MinioClient minioClient() {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioUrl)
                    .credentials(minioUsername, minioPassword)
                    .build();

            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioBucket).build())) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(minioBucket)
                                .build()
                );
            }

            return minioClient;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Cannot connect to minio server by error: " + e.getMessage());
        }
    }
}
