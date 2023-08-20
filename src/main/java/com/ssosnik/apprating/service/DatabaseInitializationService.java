package com.ssosnik.apprating.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.stereotype.Service;

import java.sql.Connection;

@Service
public class DatabaseInitializationService implements DatabasePopulator {

    @Value("${app-rating.random-init}")
    private final boolean enableDatabasePopulation;

    @Value("${app-rating.random-init.app-count}")
    private int appCount;

    @Value("${app-rating.random-init.review-count-per-app}")
    private int reviewCountPerApp;

    private final DataGenerationService dataGenerationService;

    @Autowired
    public DatabaseInitializationService(
            DataGenerationService dataGenerationService,
            Environment environment
    ) {
        this.dataGenerationService = dataGenerationService;
        this.enableDatabasePopulation = Boolean.parseBoolean(environment.getProperty("app-rating.random-init"));
    }

    @Override
    public void populate(Connection connection)  {
        if (enableDatabasePopulation) {
            dataGenerationService.initializeData(appCount, reviewCountPerApp);
        }
    }
}
