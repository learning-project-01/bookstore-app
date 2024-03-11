package com.example.bookstoreapp.controllers;

import com.example.bookstoreapp.models.Media;
import com.example.bookstoreapp.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/media")
public class MediaController {
    @Autowired
    private MediaService mediaService;
    @PostMapping
    public Media saveMedia(@RequestBody Media media)
    {
        return mediaService.save(media);
    }
    @GetMapping("/{id}")
    public Media mediaById(@PathVariable Long id)
    {
        return mediaService.mediaById(id);
    }
    @PutMapping("/{id}")
    public Media updateMedia(@PathVariable Long id,@RequestBody Media media)
    {
        return mediaService.updateMedia(id,media);
    }
    @DeleteMapping("/{id}")
    public Media removeMedia(@PathVariable Long id)
    {
        return mediaService.removeMedia(id);
    }

}
