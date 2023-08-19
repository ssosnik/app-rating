package com.ssosnik.apprating.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;

@Service
public class DailyCsvFileWatcherService {

    @Value("${folder.path.daily-csv}")
    private String csvFolderPath;

    @Autowired
    private DailyCsvFileProcessor dailyCsvFileProcessor;

    @PostConstruct
    public void watchDirectory() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path directory = getDailyCsvFolder();

            directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            while (true) {
                WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    Path filePath = directory.resolve((Path) event.context());
                    dailyCsvFileProcessor.processCsvFile(filePath);
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Path getDailyCsvFolder() {
        Path directory = Paths.get(csvFolderPath);

        if (!Files.exists(directory)) {
            // Create the directory if it doesn't exist
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create daily-csv-directory at: " + directory.toAbsolutePath().toString(), e);
            }
        }
        if (!Files.isDirectory(directory)) {
            throw new RuntimeException("daily-csv-directory path is not a directory: " + directory.toAbsolutePath().toString());
        }
        return directory;
    }
}
