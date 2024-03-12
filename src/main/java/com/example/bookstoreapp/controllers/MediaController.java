package com.example.bookstoreapp.controllers;

import com.example.bookstoreapp.models.Media;
import com.example.bookstoreapp.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
