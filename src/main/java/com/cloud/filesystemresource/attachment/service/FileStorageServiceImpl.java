package com.cloud.filesystemresource.attachment.service;

import com.cloud.filesystemresource.attachment.entity.Attachment;
import com.cloud.filesystemresource.attachment.repository.AttachmentRepository;
import com.cloud.filesystemresource.attachment.repository.FileSystemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final FileSystemRepository fileSystemRepository;
    private final AttachmentRepository attachmentRepository;

    @Override
    @Transactional
    public void uploadFiles(List<MultipartFile> files, Map<String, Attachment> fileInfos) throws IOException {
        for (MultipartFile file : files) {
            Attachment attachment = fileInfos.get(file.getOriginalFilename());
            this.attachmentRepository.save(attachment);
            this.fileSystemRepository.upload(attachment.getId(), attachment.getName(), file.getBytes());
        }
    }

    @Override
    @Transactional
    public Resource downloadFile(UUID id) {
        Attachment attachment = this.attachmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ID"));
        return this.fileSystemRepository.download(id, attachment.getName());
    }

    @Override
    public Attachment findById(UUID id) {
        return this.attachmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID"));
    }
}
