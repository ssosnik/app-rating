package com.ssosnik.apprating.configuration;
import com.ssosnik.apprating.service.DatabaseInitializationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;

import javax.sql.DataSource;

@Configuration
@AllArgsConstructor
public class DatabaseInitializationConfig {

    private final DataSource dataSource;

    @Autowired
    private DatabaseInitializationService initializationService;

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(initializationService);
        return initializer;
    }
}





