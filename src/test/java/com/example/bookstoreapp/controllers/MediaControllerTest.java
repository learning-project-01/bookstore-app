package com.example.bookstoreapp.controllers;

import com.example.bookstoreapp.models.Media;
import com.example.bookstoreapp.services.MediaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MediaControllerTest {
    @InjectMocks
    private MediaController mediaController;
    @Mock
    private MediaService mediaService;
    @Test
    void save()
    {
        Media media = new Media();
        media.setId(1L);
        media.setMediaUrl("www.google.com");
        media.setSequenceId(2L);
        media.setMediaType(1);
        media.setItemId(6L);

        when(mediaService.save(Mockito.any(Media.class))).thenReturn(media);
        mediaController.saveMedia(media);
        assertEquals("www.google.com", media.getMediaUrl());
        assertEquals(1L, media.getId());
        assertEquals(2L, media.getSequenceId());
        assertEquals(6L, media.getItemId());
        assertEquals(1, media.getMediaType());

    }
    @Test
    void updateMedia()
    {
        Media media = new Media();
        when(mediaService.updateMedia(1L, media)).thenReturn(media);
        Media updateMedia = mediaController.updateMedia(1L, media);
        assertEquals(updateMedia, media);
    }
    @Test
    public void testMediaById() {
        Long id = 1L;
        Media media = new Media();
        when(mediaService.mediaById(id)).thenReturn(media);
        Media retrievedMedia = mediaController.mediaById(id);
        assertNotNull(retrievedMedia);
        assertEquals(retrievedMedia,media);
    }
    @Test
    public void testRemoveMedia() {
        Long id = 1L;
        Media media = new Media();
        when(mediaService.removeMedia(id)).thenReturn(media);
        Media removedMedia = mediaController.removeMedia(id);
        assertNotNull(removedMedia);
        assertEquals(removedMedia,media);
    }

}