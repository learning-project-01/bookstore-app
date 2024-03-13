package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.entities.MediaEntity;
import com.example.bookstoreapp.exceptions.AppRuntimeException;
import com.example.bookstoreapp.models.Media;
import com.example.bookstoreapp.models.MediaType;
import com.example.bookstoreapp.repositories.MediaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MediaServiceImplTest {
    @InjectMocks
    private MediaServiceImpl mediaService;
    @Mock
    private MediaRepository mediaRepository;

    @Test
    void addMedia() {
        Media media = new Media();
        media.setMediaType(MediaType.IMG);
        media.setMediaUrl("www.googel.com");
        media.setThumbnail(true);
        media.setSequenceId(1L);
        media.setItemId(5L);

        MediaEntity mediaEntity = new MediaEntity();
        mediaEntity.setMediaType(MediaType.IMG.getValue());
        mediaEntity.setMediaUrl("www.googel.com");
        mediaEntity.setThumbnail(true);
        mediaEntity.setSequenceId(1L);
        mediaEntity.setItemId(5L);

        Mockito.when(mediaRepository.save(Mockito.any(MediaEntity.class))).thenReturn(mediaEntity);

        Media addedMedia = mediaService.addMedia(media);

        assertNotNull(addedMedia);
        assertEquals(media.getMediaType(), addedMedia.getMediaType());
        assertEquals(media.getMediaUrl(), addedMedia.getMediaUrl());

    }

    @Test
    void findById() {
        Long id = 1L;
        MediaEntity mediaEntity = new MediaEntity();
        mediaEntity.setId(id);
        mediaEntity.setMediaType(MediaType.VID.getValue());
        mediaEntity.setMediaUrl("https://google.com/image.jpg");
        mediaEntity.setThumbnail(true);
        mediaEntity.setSequenceId(2L);
        mediaEntity.setItemId(5L);

        Mockito.when(mediaRepository.findById(id)).thenReturn(Optional.of(mediaEntity));

        Media foundMedia = mediaService.findById(id);

        assertNotNull(foundMedia);
        assertEquals(mediaEntity.getId(), foundMedia.getId());
        assertEquals(mediaEntity.getMediaType(), foundMedia.getMediaType().getValue());
        assertEquals(mediaEntity.getMediaUrl(), foundMedia.getMediaUrl());
    }

    @Test
    void findByIdNotFound() {
        Long id = 6L;
        Mockito.when(mediaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AppRuntimeException.class, () -> mediaService.findById(id));
    }

    @Test
    void updateMedia() {
        Long id = 1L;
        Media media = new Media();
        media.setMediaType(MediaType.IMG);
        media.setMediaUrl("www.googel.com");
        media.setThumbnail(true);
        media.setSequenceId(1L);
        media.setItemId(5L);

        MediaEntity mediaEntity = new MediaEntity();
        mediaEntity.setId(id);
        mediaEntity.setMediaType(MediaType.VID.getValue());
        mediaEntity.setMediaUrl("https://google.com/image.jpg");
        mediaEntity.setThumbnail(true);
        mediaEntity.setSequenceId(2L);
        mediaEntity.setItemId(5L);

        when(mediaRepository.findById(id)).thenReturn(Optional.of(mediaEntity));
        when(mediaRepository.save(Mockito.any(MediaEntity.class))).thenReturn(mediaEntity);

        Media updatedMedia = mediaService.updateMedia(id, media);

        assertNotNull(updatedMedia);
        assertEquals(mediaEntity.getId(), updatedMedia.getId());
        assertEquals(mediaEntity.getMediaType(), updatedMedia.getMediaType().getValue());
        assertEquals(mediaEntity.getMediaUrl(), updatedMedia.getMediaUrl());
    }

    @Test
    void updateIdNotFound() {
        Long id = 5L;
        Mockito.when(mediaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AppRuntimeException.class, () -> mediaService.findById(id));
    }

    @Test
    void deleteMedia() {
        Long id = 1L;
        MediaEntity mediaEntity = new MediaEntity();
        mediaEntity.setId(id);
        mediaEntity.setMediaType(MediaType.VID.getValue());
        mediaEntity.setMediaUrl("https://google.com/image.jpg");
        mediaEntity.setThumbnail(true);
        mediaEntity.setSequenceId(2L);
        mediaEntity.setItemId(5L);

        Mockito.when(mediaRepository.findById(id)).thenReturn(Optional.of(mediaEntity));

        Media deleteMedia = mediaService.deleteMedia(id);

        assertNotNull(deleteMedia);
        assertEquals(mediaEntity.getId(), deleteMedia.getId());
        assertEquals(mediaEntity.getMediaType(), deleteMedia.getMediaType().getValue());
        assertEquals(mediaEntity.getMediaUrl(), deleteMedia.getMediaUrl());
    }

    @Test
    void deleteIdNotFound() {
        Long id = 5L;
        Mockito.when(mediaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AppRuntimeException.class, () -> mediaService.deleteMedia(id));
    }

    @Test
    void allMedia() {

        Long id = 1L;
        MediaEntity mediaEntity = new MediaEntity();
        mediaEntity.setId(id);
        mediaEntity.setMediaType(MediaType.VID.getValue());
        mediaEntity.setMediaUrl("https://google.com/image.jpg");
        mediaEntity.setThumbnail(true);
        mediaEntity.setSequenceId(2L);
        mediaEntity.setItemId(5L);

        MediaEntity mediaEntity1 = new MediaEntity();
        mediaEntity1.setId(id);
        mediaEntity1.setMediaType(MediaType.VID.getValue());
        mediaEntity1.setMediaUrl("https://google.com/image.jpg");
        mediaEntity1.setThumbnail(true);
        mediaEntity1.setSequenceId(2L);
        mediaEntity1.setItemId(5L);

        List<MediaEntity> mediaEntityList = new ArrayList<>();
        mediaEntityList.add(mediaEntity);
        mediaEntityList.add(mediaEntity1);

        Mockito.when(mediaRepository.findAll()).thenReturn(mediaEntityList);

        List<Media> allMediaList = mediaService.allMedia();

        assertNotNull(allMediaList);
        assertEquals(mediaEntityList.size(), allMediaList.size());
    }
}