package com.example.bookstoreapp.models;

import com.example.bookstoreapp.entities.MediaEntity;
import com.example.bookstoreapp.utils.IdGenerator;
import lombok.Data;

import java.util.List;

@Data
public class Media {
    private Long id;
    private MediaType mediaType;
    private String mediaUrl;
    private boolean thumbnail;
    private Long sequenceId;
    private Long itemId;
    private List<Media> itemMedias;

    public MediaEntity toEntity() {
        MediaEntity entity = new MediaEntity();
        entity.setId(IdGenerator.getLongId());
        entity.setMediaType(this.getMediaType().getValue());
        entity.setMediaUrl(this.getMediaUrl());
        entity.setThumbnail(this.isThumbnail());
        entity.setSequenceId(this.getSequenceId());
        entity.setItemId(this.getItemId());
        return entity;
    }

    public Media fromEntity(MediaEntity entity) {
        this.setId(entity.getId());
        this.setMediaType(MediaType.toMediaType(entity.getMediaType()));
        this.setMediaUrl(entity.getMediaUrl());
        this.setThumbnail(entity.isThumbnail());
        this.setSequenceId(entity.getSequenceId());
        this.setItemId(entity.getItemId());
        return this;
    }
}
