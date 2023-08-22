package com.ssosnik.apprating.service;

import com.ssosnik.apprating.domain.App;
import com.ssosnik.apprating.domain.Review;
import com.ssosnik.apprating.domain.repository.AppRepository;
import com.ssosnik.apprating.domain.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;


@SpringBootTest
//@DirtiesContext
public class DataGenerationServiceTest {

    @Mock
    private AppRepository appRepository;

    @Mock
    private ReviewRepository reviewRepository;

    private DataGenerationService dataGenerationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        dataGenerationService = new DataGenerationService(appRepository, reviewRepository);
    }

    @Test
    public void testInitializeData() {
        int numberOfApps = 3;
        int numberOfReviews = 5;

        dataGenerationService.initializeData(numberOfApps, numberOfReviews);

        // Verify that save method is called numberOfApps times on appRepository
        verify(appRepository, times(numberOfApps)).save(any(App.class));

        // Verify that save method is called numberOfApps * numberOfReviews times on reviewRepository
        verify(reviewRepository, times(numberOfApps * numberOfReviews)).save(any(Review.class));
    }


}
