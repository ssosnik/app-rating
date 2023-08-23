package com.ssosnik.apprating.endtoend;

import com.ssosnik.apprating.dto.AppRatingChangeDTO;
import com.ssosnik.apprating.service.DailyCsvFileProcessor;
import com.ssosnik.apprating.service.MonthlyCsvReportGenerationService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.Rollback;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MonthlyReportGenerationTest {

    @Autowired
    DailyCsvFileProcessor csvFileProcessor;

    @Autowired
    MonthlyCsvReportGenerationService monthlyCsvReportGenerationService;


    @Test
    @Transactional
    @Rollback
    public void loadDailyCSVFilesAndGenerateMonthlyReport() throws IOException {

        // Step 1: Load Daily CSV file with date Jul1
        LocalDate july1 = LocalDate.of(2023, 7, 1);
        Path csvFilePath = getDailyCsvFile("app_rating-2023-07-01.csv");
        csvFileProcessor.processCsvFile(csvFilePath, july1);

        // Step 2: Load Daily CSV file with date Aug31
        LocalDate aug31 = LocalDate.of(2023, 8, 31);
        csvFilePath = getDailyCsvFile("app_rating-2023-08-31.csv");
        csvFileProcessor.processCsvFile(csvFilePath, aug31);

        // Step 3: Generate monthly report
        List<AppRatingChangeDTO> sortedAppList = monthlyCsvReportGenerationService.computeSortedAppList(aug31);

        // Step 4: Test order of apps
        assertEquals(4, sortedAppList.size());
        assertEquals("Magic Scanner", sortedAppList.get(0).getAppName());
        assertEquals("Quickphoto", sortedAppList.get(1).getAppName());
        assertEquals("SciCalc", sortedAppList.get(2).getAppName());
        assertEquals("Mobile Notepad", sortedAppList.get(3).getAppName());

    }

    private static Path getDailyCsvFile(String filename) throws IOException {
        Resource resource = new ClassPathResource("testing-files/daily-csv/" + filename);
        Path csvFilePath = Files.createTempFile("daily-csv", ".csv");
        Files.copy(resource.getInputStream(), csvFilePath, StandardCopyOption.REPLACE_EXISTING);
        return csvFilePath;
    }


}
