package com.urlshortner.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Entity
public class Urls {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String shortCode;
    private String longUrl;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
