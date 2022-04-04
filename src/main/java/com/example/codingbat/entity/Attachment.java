package com.example.codingbat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Attachment {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;

    private String originalFileName;

    private String fileName;

    private String contentType;

    private Long size;

    @ManyToOne
    private Intro intro;

    public Attachment(String originalFileName, String fileName, String contentType, Long size, Intro intro) {
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.contentType = contentType;
        this.size = size;
        this.intro = intro;
    }
}
