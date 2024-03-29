package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.entities.MediaEntity;
import com.example.bookstoreapp.exceptions.AppRuntimeException;
import com.example.bookstoreapp.models.Media;
import com.example.bookstoreapp.repositories.MediaRepository;
import com.example.bookstoreapp.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Override
    public Media addMedia(Media media) {
        MediaEntity mediaEntity = media.toEntity();
        mediaEntity = mediaRepository.save(mediaEntity);
        return media.fromEntity(mediaEntity);
    }

    @Override
    public Media findById(Long id) {
        try {
            Optional<MediaEntity> savedMedia = mediaRepository.findById(id);
            if (savedMedia.isPresent()) {
                return new Media().fromEntity(savedMedia.get());
            } else throw new AppRuntimeException("No item found with the id " + id);
        } catch (Exception e) {
            throw new AppRuntimeException("No such item is present with this id " + id);
        }
    }

    @Override
    public Media updateMedia(Long id, Media media) {
        Media savedMedia = findById(id);
        MediaEntity mediaEntity = media.toEntity();
        mediaEntity = mediaRepository.save(mediaEntity);
        return new Media().fromEntity(mediaEntity);
    }

    @Override
    public Media deleteMedia(Long id) {
        try {
            Optional<MediaEntity> mediaEntity = mediaRepository.findById(id);
            if (mediaEntity.isPresent()) {
                MediaEntity savedMediaEntity = mediaEntity.get();
                mediaRepository.deleteById(id);
                return new Media().fromEntity(savedMediaEntity);
            } else {
                throw new AppRuntimeException("No such element is present to perform delete operation");
            }
        } catch (Exception e) {
            throw new AppRuntimeException("Error while deleting media with id " + id);
        }
    }

    @Override
    public List<Media> allMedia() {
        Iterable<MediaEntity> entities = mediaRepository.findAll();
        List<Media> mediaList = new ArrayList<>();
        for (MediaEntity entity : entities) {
            Media media = new Media().fromEntity(entity);
            mediaList.add(media);
        }
        return mediaList;
    }

}
