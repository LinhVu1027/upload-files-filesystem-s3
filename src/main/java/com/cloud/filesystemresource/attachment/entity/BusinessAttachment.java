package com.cloud.filesystemresource.attachment.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.UUID;

@Getter
@Setter
@Entity
public class BusinessAttachment extends Attachment {
    private String a1;
    private String a2;

    private BusinessAttachment() {
    }

    public BusinessAttachment(UUID id, String name, String contentType) {
        super(id, name, contentType);
    }
}
