package com.ssosnik.apprating.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class App {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String appName;
    private String appUUID;

//    Above code is not needed, since the average and other computations are done via JPQL query.
//    @OneToMany(mappedBy = "app", cascade = CascadeType.ALL)
//    private List<Review> reviews = new ArrayList<>();
}