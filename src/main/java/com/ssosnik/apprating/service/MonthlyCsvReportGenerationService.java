package com.ssosnik.apprating.service;


import com.opencsv.CSVWriter;
import com.ssosnik.apprating.domain.App;
import com.ssosnik.apprating.domain.repository.AppRepository;
import com.ssosnik.apprating.dto.AppRatingChangeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MonthlyCsvReportGenerationService {
    @Value("${app-rating.folder.monthly-report-csv}")
    private String csvFolderPath;

    @Autowired
    private AppRepository appRepository;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");


    public void generateMonthlyCsvReport(LocalDate lastDayOfMonth) {
        LocalDate firstDayOfMonth = lastDayOfMonth.withDayOfMonth(1);
        List<Object[]> currentRatings = appRepository
                .findAllAppsWithAverageRating(firstDayOfMonth, lastDayOfMonth);

        LocalDate lastDayOfPreviousMonth = firstDayOfMonth.minusDays(1);
        LocalDate firstDayOfPreviousMonth = lastDayOfPreviousMonth.withDayOfMonth(1);
        List<Object[]> previousRatings = appRepository
                .findAllAppsWithAverageRating(firstDayOfPreviousMonth, lastDayOfPreviousMonth);

        List<AppRatingChangeDTO> sortedList = combineCurrentAndPreviousRatinbgList(currentRatings, previousRatings);
        
        File csvFile = getCsvFile(firstDayOfMonth);
        saveCsvReport(csvFile, sortedList);
    }

    private static void saveCsvReport(File csvFile, List<AppRatingChangeDTO> sortedList) {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile))) {
            // Write header
            csvWriter.writeNext(new String[]{"appName", "appUUID", "averageRating", "previousAverageRating"});

            // Write data rows
            for (AppRatingChangeDTO dto : sortedList) {
                csvWriter.writeNext(new String[]{
                        dto.getAppName(),
                        dto.getAppUUID(),
                        String.valueOf(dto.getAverageRating()),
                        String.valueOf(dto.getPreviousAverageRating())
                });
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write monthly report CSV file", e);
        }
    }

    private File getCsvFile(LocalDate firstDayOfMonth) {
        String csvFileName = getCSVReportName(firstDayOfMonth);
        Path csvReportDir = getMonthlyCsvFolder();
        return new File(csvReportDir.toFile(), csvFileName);
    }

    private static List<AppRatingChangeDTO> combineCurrentAndPreviousRatinbgList(List<Object[]> currentRatings, List<Object[]> previousRatings) {
        Map<String, AppRatingChangeDTO> ratingChangeMap = new HashMap<>();

        for (Object[] array : currentRatings) {
            App app = (App) array[0];
            Double avgRating = (Double) array[1];
            AppRatingChangeDTO ratingChangeDTO = AppRatingChangeDTO.builder()
                    .appName(app.getAppName())
                    .appUUID(app.getAppUUID())
                    .averageRating(avgRating)
                    .build();
            ratingChangeMap.put(app.getAppUUID(), ratingChangeDTO);
        }

        for (Object[] array : previousRatings) {
            App app = (App) array[0];
            Double avgRating = (Double) array[1];

            AppRatingChangeDTO ratingChangeDTO = ratingChangeMap.get(app.getAppUUID());
            if (ratingChangeDTO != null) {
                ratingChangeDTO.setPreviousAverageRating(avgRating);
            }
        }

        List<AppRatingChangeDTO> sortedList = ratingChangeMap.values().stream()
                .filter(dto -> dto.getAverageRating() != null && dto.getPreviousAverageRating() != null)
                .sorted(Comparator.comparingDouble(dto -> dto.getPreviousAverageRating() - dto.getAverageRating()))
                .collect(Collectors.toList());
        return sortedList;
    }

    private static String getCSVReportName(LocalDate firstDayOfMonth) {
        String formattedDate = firstDayOfMonth.format(FORMATTER);
        return String.format("trending100apps-%s.csv", formattedDate);
    }

    private Path getMonthlyCsvFolder() {
        Path directory = Paths.get(csvFolderPath);

        if (!Files.exists(directory)) {
            // Create the directory if it doesn't exist
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create daily-csv-directory at: " + directory.toAbsolutePath(), e);
            }
        }
        if (!Files.isDirectory(directory)) {
            throw new RuntimeException("daily-csv-directory path is not a directory: " + directory.toAbsolutePath());
        }
        return directory;
    }
}
