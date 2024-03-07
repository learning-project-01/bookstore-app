package com.example.bookstoreapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "media")
@Data
@NoArgsConstructor
public class MediaEntity {
    @Id
    private Long id;
    private int mediaType;
    private String mediaUrl;
    private boolean thumbnail;
    private Long sequenceId;
    private Long itemId;

}
