package com.ssosnik.apprating.service;

import com.ssosnik.apprating.domain.repository.AppRepository;
import com.ssosnik.apprating.domain.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AppRatingService {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public Double computeAverageRatingForUUID(String appUUID, LocalDate since, LocalDate until) {

        if (!appRepository.existsByAppUUID(appUUID)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "App with UUID " + appUUID + " not found");
        }

        Optional<Double> averageRatingOptional = reviewRepository.findAverageRatingByAppUUIDAndDateBetween(appUUID, since, until);

        // Get value, or throw an exception
        return averageRatingOptional.orElse(-1.0);
    }
}