package site.thanhtungle.storageservice.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.commons.model.dto.FileDto;

import java.util.List;

public interface StorageService {

    /**
     * Upload a file
     *
     * @param multipartFile
     * @param filePath
     * @return - fileDto
     * */
    FileDto uploadFile(MultipartFile multipartFile, String filePath);

    /**
     * Upload multiple files
     *
     * @param multipartFileList
     * @param filePathList
     * @return - list fileDto
     * */
    List<FileDto> uploadFiles(List<MultipartFile> multipartFileList, List<String> filePathList);

    /**
     * Get a file by file path
     *
     * @param filePath
     * @return Resource
     * */
    Resource getFile(String filePath);

    /**
     * Delete a file
     *
     * @param filePath
     * */
    void deleteFile(String filePath);

    /**
     * Delete list files
     *
     * @param filePathList
     * */
    void deleteFiles(List<String> filePathList);

    /**
     * Get a public file url
     *
     * @param filePath
     * @return - encoded file url
     * */
    String getPublicFileUrl(String filePath);
}
