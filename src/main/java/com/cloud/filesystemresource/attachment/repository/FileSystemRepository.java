package com.cloud.filesystemresource.attachment.repository;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.UUID;

public interface FileSystemRepository {
    String upload(UUID uuid, String name, byte[] content) throws IOException;
    Resource download(UUID uuid, String name);
}
