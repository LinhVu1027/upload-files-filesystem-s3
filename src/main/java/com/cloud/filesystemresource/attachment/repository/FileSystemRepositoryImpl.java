package com.cloud.filesystemresource.attachment.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Repository
public class FileSystemRepositoryImpl implements FileSystemRepository {

    private final String uri;
    private final ResourceLoader resourceLoader;

    public FileSystemRepositoryImpl(@Value("${storage.url}") String uri, ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        this.uri = uri;
    }

    @Override
    public String upload(UUID uuid, String name, byte[] content) throws IOException {
        String path = this.uri + uuid.toString() + "-" + name;
        Resource resource = this.resourceLoader.getResource(path);
        WritableResource writableResource = (WritableResource) resource;
        try (OutputStream outputStream = writableResource.getOutputStream()) {
            outputStream.write(content);
        }
        return path;
    }

    @Override
    public Resource download(UUID uuid, String name) {
        String path = this.uri + uuid.toString() + "-" + name;
        return this.resourceLoader.getResource(path);
    }
}
