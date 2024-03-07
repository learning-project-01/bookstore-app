package com.example.bookstoreapp.services;

import com.example.bookstoreapp.models.Media;

import java.util.List;

public interface MediaService {
    Media addMedia(Media media);

    Media updateMedia(Long id, Media media);

    Media deleteMedia(Long id);

    Media findById(Long id);

    List<Media> allMedia();

    Media itemMedias(Long itemId);
}
