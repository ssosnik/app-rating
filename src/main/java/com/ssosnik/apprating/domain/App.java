package com.ssosnik.apprating.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(indexes = @Index(name = "idx_app_uuid", columnList = "appUUID"))
public class App {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String appName;
    private String appUUID;

    @OneToMany(mappedBy = "app", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();
}