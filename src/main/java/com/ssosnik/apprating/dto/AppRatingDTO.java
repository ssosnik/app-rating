package com.ssosnik.apprating.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AppRatingDTO {
    private String appName;
    private String appUUID;
    private double averageRating;
}