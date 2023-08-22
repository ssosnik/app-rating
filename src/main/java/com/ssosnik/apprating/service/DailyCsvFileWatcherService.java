package com.ssosnik.apprating.service;

import com.ssosnik.apprating.event.DailyCsvLoadingCompletedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;

@Service
public class DailyCsvFileWatcherService {

    @Value("${app-rating.folder.daily-csv}")
    private String csvFolderPath;

    @Autowired
    private DailyCsvFileProcessor dailyCsvFileProcessor;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Async
    public void watchDirectory() {
        Path directory = getDailyCsvFolder();

        try (WatchService watchService = createWatchService(directory)) {
            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    Path filePath = directory.resolve((Path) event.context());
                    dailyCsvFileProcessor.processCsvFile(filePath);

                    // Publish the CsvLoadingCompletedEvent after CSV loading is done
                    eventPublisher.publishEvent(new DailyCsvLoadingCompletedEvent(filePath));
                }
                key.reset();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static WatchService createWatchService(Path directory)  {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            return watchService;
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize WatchService.", e);
        }
    }

    private Path getDailyCsvFolder() {
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
