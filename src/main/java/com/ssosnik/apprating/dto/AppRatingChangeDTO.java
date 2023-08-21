package com.ssosnik.apprating.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AppRatingChangeDTO {
    private String appName;
    private String appUUID;
    private Double averageRating;
    private Double previousAverageRating;
}