package com.ssosnik.apprating.configuration;

import com.ssosnik.apprating.service.DailyCsvFileWatcherService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyCSVFileWatcherRunner implements CommandLineRunner {

    private final DailyCsvFileWatcherService fileWatcherService;

    @Override
    public void run(String... args) {
        fileWatcherService.watchDirectory();
    }
}
