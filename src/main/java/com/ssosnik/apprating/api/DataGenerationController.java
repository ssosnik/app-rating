package com.ssosnik.apprating.api;

import com.ssosnik.apprating.domain.DataInitializer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data-generator")
public class DataGenerationController {

    private final DataInitializer dataInitializer; // Inject your DataInitializer here

    public DataGenerationController(DataInitializer dataInitializer) {
        this.dataInitializer = dataInitializer;
    }

    @PostMapping("/random-data")
    public String generateRandomData(@RequestParam int numberOfApps,
                                     @RequestParam int numberOfReviews) {
        dataInitializer.initializeData(numberOfApps, numberOfReviews);

        return "Random data generation complete!";
    }
}