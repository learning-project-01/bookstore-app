package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.entities.MediaEntity;
import com.example.bookstoreapp.exceptions.AppRuntimeException;
import com.example.bookstoreapp.models.Media;
import com.example.bookstoreapp.repositories.MediaEntityRepository;
import com.example.bookstoreapp.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private MediaEntityRepository mediaEntityRepository;
    @Override
    public Media save(Media media) {
        MediaEntity mediaEntity = media.toEntity();
        mediaEntity = mediaEntityRepository.save(mediaEntity);
        return media.fromEntity(mediaEntity);
}
    @Override
    public Media mediaById(Long id) {
        Optional<MediaEntity> mediaEntity = mediaEntityRepository.findById(id);
        return new Media().fromEntity(mediaEntity.get());
    }

    @Override
    public Media updateMedia(Long id, Media media) {
        Optional<MediaEntity> savedMedia = mediaEntityRepository.findById(id);
        MediaEntity mediaEntity = media.toEntity();
        mediaEntity = mediaEntityRepository.save(mediaEntity);
        return new Media().fromEntity(mediaEntity);
    }

    @Override
    public Media removeMedia(Long id) {
        Optional<MediaEntity> mediaEntity = mediaEntityRepository.findById(id);
        MediaEntity entity = mediaEntity.get();
        mediaEntityRepository.deleteById(id);
        return new Media().fromEntity(entity);

    }
}
