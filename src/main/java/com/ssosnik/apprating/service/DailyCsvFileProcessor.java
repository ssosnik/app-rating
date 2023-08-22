package com.ssosnik.apprating.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.ssosnik.apprating.domain.App;
import com.ssosnik.apprating.domain.Review;
import com.ssosnik.apprating.domain.repository.AppRepository;
import com.ssosnik.apprating.domain.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DailyCsvFileProcessor {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional
    public void processCsvFile(Path filePath, LocalDate currentDate) {
        // Parse CSV file using a CSV parsing library (OpenCSV)
        // create entities (use a Map uuid2App)
        // persist (save) to database
        Map<String, App> appMap = new HashMap<>();
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(filePath.toFile()))
                .withSkipLines(1) // Skip the header line
                .build()) {

            List<String[]> rows = csvReader.readAll();
            for (String[] row : rows) {
                String appUUID = row[1];

                // Check if the app exists in map
                App app = appMap.get(appUUID);
                if (app == null) {
                    // Check if the app exists in the database
                    // (this happens when uuid appears for the first time in CSV file)
                    app = appRepository.findByAppUUID(appUUID);
                    if (app == null) {
                        app = new App();
                        app.setAppName(row[0]);
                        app.setAppUUID(appUUID);
                        appRepository.save(app);
                    }

                    appMap.put(appUUID, app);
                }

                Review review = new Review();
                review.setRating(Double.parseDouble(row[2]));
                review.setReviewerAge(Integer.parseInt(row[3]));
                review.setReviewerCountry(row[4]);
                review.setDate(currentDate);
                review.setApp(app);
                reviewRepository.save(review);
            }

        } catch (CsvException | IOException e) {
            throw new RuntimeException("Failed to read/parse CVS file", e);
        }

        log.info("Daily-csv-file {} loaded to database", filePath.getFileName());
    }


}