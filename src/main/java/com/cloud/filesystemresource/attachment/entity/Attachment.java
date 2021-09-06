package com.cloud.filesystemresource.attachment.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.UUID;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Attachment {
    @Id
    private UUID id;
    private String name;
    private String contentType;

    protected Attachment() {
    }

    protected Attachment(UUID id, String name, String contentType) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
    }
}
