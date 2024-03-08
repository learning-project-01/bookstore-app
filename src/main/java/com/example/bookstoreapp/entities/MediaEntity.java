package com.example.bookstoreapp.entities;

import com.example.bookstoreapp.models.MediaType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "media")
@Entity
@NoArgsConstructor
public class MediaEntity {
    @Id
   private Long id;
  private   MediaType mediaType; // IMG, VIDEO
    private String mediaUrl;
    private boolean thumbNail;
    private Long sequenceId;
    private Long itemId;
}
