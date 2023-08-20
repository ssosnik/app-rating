package com.ssosnik.apprating.api;

import com.ssosnik.apprating.service.DataGenerationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data-generator")
public class DataGenerationController {

    private final DataGenerationService dataGenerationService; // Inject your DataInitializer here

    public DataGenerationController(DataGenerationService dataGenerationService) {
        this.dataGenerationService = dataGenerationService;
    }

    @PostMapping("/random-data")
    public String generateRandomData(@RequestParam int numberOfApps,
                                     @RequestParam int numberOfReviews) {
        dataGenerationService.initializeData(numberOfApps, numberOfReviews);

        return "Random data generation complete!";
    }
}