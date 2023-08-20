package com.ssosnik.apprating.api;

import com.ssosnik.apprating.service.AppRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class AppRatingController {

    @Autowired
    AppRatingService appRatingService;

    @GetMapping("/{appUuid}/avg")
    public Double appAverageRating(@PathVariable String appUuid,
                                   @RequestParam("since") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate since,
                                   @RequestParam("until") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate until) {
        return appRatingService.computeAverageRatingForUUID(appUuid, since, until);
    }

    @GetMapping("/top-apps/{ageGroup}")
    public String topApsForAgeGroup(@PathVariable AGE_GROUP ageGroup,
                                    @RequestParam("since") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate since,
                                    @RequestParam("until") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate until) {
        return "Not implemented yet";
    }

}
