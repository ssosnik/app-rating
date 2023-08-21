package com.ssosnik.apprating.event.listener;

import com.ssosnik.apprating.event.DailyCsvLoadingCompletedEvent;
import com.ssosnik.apprating.service.MonthlyCsvReportGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DailyCsvLoadingCompletedEventListener implements ApplicationListener<DailyCsvLoadingCompletedEvent> {

    @Autowired
    private MonthlyCsvReportGenerationService monthlyCsvReportGenerationService;

    @Override
    public void onApplicationEvent(DailyCsvLoadingCompletedEvent event) {
        // Launch CSV generation job, if this is the last day of month
        LocalDate date = LocalDate.now();
        boolean isLastDayOfMonth = date.getDayOfMonth() == date.lengthOfMonth();

        if (isLastDayOfMonth) {
            monthlyCsvReportGenerationService.generateMonthlyCsvReport(date);
        }

    }
}