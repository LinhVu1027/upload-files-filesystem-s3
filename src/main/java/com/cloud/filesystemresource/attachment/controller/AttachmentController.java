package com.cloud.filesystemresource.attachment.controller;

import com.cloud.filesystemresource.attachment.entity.Attachment;
import com.cloud.filesystemresource.attachment.entity.BusinessAttachment;
import com.cloud.filesystemresource.attachment.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/attachments")
@RequiredArgsConstructor
public class AttachmentController {

    private final FileStorageService fileStorageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFiles(@RequestPart Map<String, InfoUploadFileRequest> request,
            @RequestPart List<MultipartFile> files) throws IOException {
        if (request.size() != files.size()) {
            throw new IllegalArgumentException("The number of uploaded file and uploaded information are not equal");
        }

        Map<String, Attachment> fileInfos = new HashMap<>();
        for (MultipartFile file : files) {
            InfoUploadFileRequest infoUploadFileRequest = request.get(file.getOriginalFilename());
            BusinessAttachment attachment = new BusinessAttachment(UUID.randomUUID(), file.getOriginalFilename(), file.getContentType());
            attachment.setA1(infoUploadFileRequest.getA1());
            attachment.setA2(infoUploadFileRequest.getA2());
            fileInfos.put(attachment.getName(), attachment);
        }

        this.fileStorageService.uploadFiles(files, fileInfos);
    }

    @GetMapping(value = "/{id}/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadFile(@PathVariable UUID id) {
        Attachment attachment = this.fileStorageService.findById(id);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Disposition", "attachment; filename=\"" + attachment.getName() + "\"");
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(this.fileStorageService.downloadFile(id));
    }
}
