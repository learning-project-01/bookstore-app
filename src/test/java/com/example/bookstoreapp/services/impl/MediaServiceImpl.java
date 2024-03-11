package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.entities.MediaEntity;
import com.example.bookstoreapp.models.Media;
import com.example.bookstoreapp.repositories.MediaEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MediaServiceImplTest {
    @InjectMocks
    private MediaServiceImpl mediaServiceImpl;
    @Mock
    private MediaEntityRepository mediaEntityRepository;

    @Test
    void save() {
        Media media = new Media();
        media.setMediaType(1);
        media.setMediaUrl("www.facebook.com");
        media.setThumbnail(true);
        media.setSequenceId(1L);
        media.setItemId(2L);
        MediaEntity mediaEntity = new MediaEntity();
        mediaEntity.setMediaType(1);
        mediaEntity.setMediaUrl("www.facebook.com");
        mediaEntity.setThumbnail(true);
        mediaEntity.setSequenceId(1L);
        mediaEntity.setItemId(2L);
        when(mediaEntityRepository.save(Mockito.any(MediaEntity.class))).thenReturn(mediaEntity);
        Media media1 = mediaServiceImpl.save(media);
        assertNotNull(media1);
        assertEquals(media.getMediaType(), media1.getMediaType());
        assertEquals(media.getMediaUrl(), media1.getMediaUrl());
    }
    @Test
    public void testMediaById() {
        Long id = 1L;
        MediaEntity mediaEntity = new MediaEntity();
        Optional<MediaEntity> optionalMediaEntity = Optional.of(mediaEntity);
        when(mediaEntityRepository.findById(id)).thenReturn(optionalMediaEntity);
        Media retrievedMedia = mediaServiceImpl.mediaById(id);
        assertNotNull(retrievedMedia);
    }
}
