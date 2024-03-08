package com.example.bookstoreapp.models;

import com.example.bookstoreapp.entities.MediaEntity;
import com.example.bookstoreapp.utils.IdGenerator;
import lombok.Data;
@Data
public class Media {

    private Long id;
    private MediaType mediaType; // IMG, VIDEO
    private String mediaUrl;
    private boolean thumbNail;
    private Long sequenceId;
    private Long itemId;
    public MediaEntity toEntity() {
        MediaEntity entity = new MediaEntity();
        entity.setId(IdGenerator.getLongId());
        entity.setMediaUrl(this.getMediaUrl());
        entity.setMediaType(this.getMediaType());
        entity.setItemId(this.getItemId());
        entity.setThumbNail(isThumbNail());
        entity.setSequenceId(IdGenerator.getLongId());
        return entity;
    }
    public Media fromEntity(MediaEntity entity) {
        this.setId(entity.getId());
        this.setMediaUrl(entity.getMediaUrl());
        this.setMediaType(entity.getMediaType());
        this.setItemId(entity.getItemId());
        this.setSequenceId(entity.getSequenceId());
        this.setThumbNail(entity.isThumbNail());
        return this;
    }
}

