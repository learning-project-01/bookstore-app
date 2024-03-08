package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.entities.MediaEntity;
import com.example.bookstoreapp.models.Media;
import com.example.bookstoreapp.repositories.MediaEntityRepository;
import com.example.bookstoreapp.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private MediaEntityRepository mediaEntityRepository;

    @Override
    public Media save(Media media) {
        MediaEntity mediaEntity=mediaEntityRepository.save(media.toEntity());
        return media.fromEntity(mediaEntity);
    }

    @Override
    public Media mediaById(Long id) {
        return null;
    }

    @Override
    public Media updateMedia(Long id, Media media) {
        return null;
    }

    @Override
    public Media removeMedia(Long id) {
        return null;
    }
}
