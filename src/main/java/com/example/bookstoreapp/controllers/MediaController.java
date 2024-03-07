package com.example.bookstoreapp.controllers;

import com.example.bookstoreapp.models.Media;
import com.example.bookstoreapp.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/media")
public class MediaController {
    @Autowired
    private MediaService mediaService;

    @GetMapping
    public List<Media> allMedia() {
        return mediaService.allMedia();
    }

    @GetMapping("/{id}")
    public Media mediaById(@PathVariable Long id) {
        return mediaService.findById(id);
    }

    @GetMapping("/item/{itemId}")
    public Media itemMedias(@PathVariable long itemId) {
        return mediaService.itemMedias(itemId);
    }

    @PostMapping
    public Media createMedia(@RequestBody Media media) {
        return mediaService.addMedia(media);
    }

    @PutMapping("/{id}")
    public Media updateMedia(@PathVariable Long id, @RequestBody Media media) {
        return mediaService.updateMedia(id, media);
    }

    @DeleteMapping("/{id}")
    public Media deletMedia(@PathVariable Long id) {
        return mediaService.deleteMedia(id);
    }
}
