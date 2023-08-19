package com.ssosnik.apprating;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class AppRatingController {

    @GetMapping("/{appUuid}/avg")
    public String appAverageRating(@PathVariable String appUuid,
                                   @RequestParam("since") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate since,
                                   @RequestParam("until") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate until) {
        return "Not implemented yet";
    }

    @GetMapping("/top-apps/{ageGroup}")
    public String topApsForAgeGroup(@PathVariable AGE_GROUP ageGroup,
                                    @RequestParam("since") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate since,
                                    @RequestParam("until") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate until) {
        return "Not implemented yet";
    }

}
