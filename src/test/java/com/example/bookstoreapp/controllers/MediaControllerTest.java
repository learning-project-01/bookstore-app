package com.example.bookstoreapp.controllers;

import com.example.bookstoreapp.models.Media;
import com.example.bookstoreapp.models.MediaType;
import com.example.bookstoreapp.services.MediaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MediaControllerTest {
    @InjectMocks
    private MediaController mediaController;
    @Mock
    private MediaService mediaService;

    @Test
    void allMedia() {
        Media media = new Media();
        media.setMediaType(MediaType.IMG);
        media.setMediaUrl("www.googel.com");
        media.setThumbnail(true);
        media.setSequenceId(1L);
        media.setItemId(5L);

        Media media1 = new Media();
        media.setMediaType(MediaType.IMG);
        media.setMediaUrl("www.Facebook.com");
        media.setSequenceId(2L);
        media1.setItemId(2L);

        List<Media> MediaList = new ArrayList<>();
        MediaList.add(media);
        MediaList.add(media1);
        Mockito.when(mediaService.allMedia()).thenReturn(MediaList);

        List<Media> allMediaList = mediaController.allMedia();

        assertEquals(MediaList, allMediaList);
    }

    @Test
    void mediaById() {
        Long id = 1L;
        Media media = new Media();
        media.setMediaType(MediaType.IMG);
        media.setMediaUrl("www.googel.com");
        media.setThumbnail(true);
        media.setSequenceId(1L);
        media.setItemId(5L);

        Mockito.when(mediaService.findById(id)).thenReturn(media);

        Media foundMedia = mediaController.mediaById(id);

        assertEquals(media, foundMedia);

    }

    @Test
    void createMedia() {
        Media media = new Media();
        media.setMediaType(MediaType.IMG);
        media.setMediaUrl("www.googel.com");
        media.setThumbnail(true);
        media.setSequenceId(1L);
        media.setItemId(5L);

        Mockito.when(mediaService.addMedia(Mockito.any(Media.class))).thenReturn(media);

        Media cratedMedia = mediaController.createMedia(media);

        assertEquals(media, cratedMedia);
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

        Mockito.when(mediaService.updateMedia(Mockito.eq(id), Mockito.any(Media.class))).thenReturn(media);

        Media result = mediaController.updateMedia(id, media);

        assertEquals(media, result);
    }

    @Test
    void deleteMedia() {
        Long id = 1L;
        Media media = new Media();
        media.setMediaType(MediaType.IMG);
        media.setMediaUrl("www.googel.com");
        media.setThumbnail(true);
        media.setSequenceId(1L);
        media.setItemId(5L);
        Mockito.when(mediaService.deleteMedia(id)).thenReturn(media);

        Media result = mediaController.deletMedia(id);

        assertEquals(media, result);
    }
}