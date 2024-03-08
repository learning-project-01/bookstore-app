package com.example.bookstoreapp.services;

import com.example.bookstoreapp.models.Media;
import org.springframework.stereotype.Service;

@Service
public interface MediaService {
    public Media save(Media media);

    public Media mediaById(Long id);

    public   Media updateMedia(Long id, Media media);

    public   Media removeMedia(Long id);
}
