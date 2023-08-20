package com.ssosnik.apprating.service;

import com.ssosnik.apprating.domain.App;
import com.ssosnik.apprating.domain.Review;
import com.ssosnik.apprating.domain.repository.AppRepository;
import com.ssosnik.apprating.domain.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DataGenerationService {

    private final AppRepository appRepository;
    private final ReviewRepository reviewRepository;
    private final Random random = new Random();

    @Transactional
    public void initializeData(int numberOfApps, int numberOfReviews) {
        for (int i = 0; i < numberOfApps; i++) {
            App app = new App();
            app.setAppName(String.format("APP%03d", i));

            // Generate a random UUID
            UUID uuid = UUID.randomUUID();
            app.setAppUUID(uuid.toString());

            // Generate random mean rating between 1.0 and 5.0
            double meanRating = 1.0 + random.nextDouble() * 4.0;

            appRepository.save(app);

            for (int j = 0; j < numberOfReviews; j++) {
                Review review = new Review();
                review.setApp(app);

                // Generate random values for age, name, and rating
                int age = random.nextInt(80) + 10; // Age between 10 and 89
                String country = "Country " + random.nextInt(10);
                double rating = generateGaussianRating(meanRating);

                review.setReviewerAge(age);
                review.setReviewerCountry(country);
                review.setRating(rating);
                review.setDate(createRandomDate());

                reviewRepository.save(review);
            }
        }
    }

    private LocalDate createRandomDate() {
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentDay = today.getDayOfYear();

        int randomDayOfYear = random.nextInt(currentDay-5) + 1; // Add 1 to avoid 0

        return LocalDate.ofYearDay(currentYear, randomDayOfYear);
    }

    private double generateGaussianRating(double mean) {
        NormalDistribution distribution = new NormalDistribution(mean, 2.0);
        double sample = distribution.sample();

        if (sample < 1.0) {
            return 1;
        } else if (sample > 5.0) {
            return 5;
        } else {
            return 0.5 * Math.round(2 * sample);
        }
    }
}