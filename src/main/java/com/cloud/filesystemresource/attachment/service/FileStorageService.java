package com.cloud.filesystemresource.attachment.service;

import com.cloud.filesystemresource.attachment.entity.Attachment;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface FileStorageService {
    void uploadFiles(List<MultipartFile> files, Map<String, Attachment> fileInfos) throws IOException;
    Resource downloadFile(UUID id);
    Attachment findById(UUID id);
}
