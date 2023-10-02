package site.thanhtungle.storageservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface MinioStorageService {

    void save(MultipartFile file, String filePath) throws Exception;

    InputStream getInputStream(String filePath) throws Exception;

    void removeObject(String filePath);

    void removeObjects(List<String> filePathList);
}
