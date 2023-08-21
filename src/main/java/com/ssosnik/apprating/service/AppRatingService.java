package com.ssosnik.apprating.service;

import com.ssosnik.apprating.api.AGE_GROUP;
import com.ssosnik.apprating.domain.App;
import com.ssosnik.apprating.domain.repository.AppRepository;
import com.ssosnik.apprating.domain.repository.ReviewRepository;
import com.ssosnik.apprating.dto.AppRatingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<AppRatingDTO> getTopAppsByAverageRating(LocalDate since, LocalDate until, AGE_GROUP ageGroup) {
        List<Object[]> topAppsWithRatingsList = appRepository
                .findTopAppsByAverageRatingInAgeGroup(since, until, ageGroup.getFrom(), ageGroup.getTo())
                .stream()
                .limit(100)
                .collect(Collectors.toList());
        return convertToAppRatingDTO(topAppsWithRatingsList);
    }

    private static List<AppRatingDTO> convertToAppRatingDTO(List<Object[]> topAppsWithRatingsList) {
        return topAppsWithRatingsList.stream()
                .map(array -> {
                    App app = (App) array[0];
                    Double avgRating = (Double) array[1];
                    return AppRatingDTO.builder()
                            .appName(app.getAppName())
                            .appUUID(app.getAppUUID())
                            .averageRating(avgRating)
                            .build();
                })
                .collect(Collectors.toList());
    }

}