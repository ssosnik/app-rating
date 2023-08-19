package com.ssosnik.apprating.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double rating;
    private int reviewerAge;
    private String reviewerCountry;
    private LocalDate date;

    @ManyToOne
    private App app;
}