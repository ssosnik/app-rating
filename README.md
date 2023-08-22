# Overview
This project (app-rating) is Spring Boot Server with following functions:
- Loading CSV file (app-ratings) to database. The operation is launched after a CSV file appears in the daily-csv-folder
- Generating monthly reports (trending-apps). The operation is launched on the last day of every month after loading daily-csv file is completed.    
- Accessing data via REST end-points:
-- GET /api/{appUuid}/avg - computes average rating for and app in a period given by since/until dates
- GET /api/top-apps/{ageGroup} - return a lits of top apps in a given age-group in a period given by since/until dates
- Generation of random data by following end-point:
- POST /data-generator/random-data - generate random data (apps, reviews) for manual tests

This project was written during a recrutation task. The specification in Polish is in docs/task.MD.pdf


## Build
Build the project as any standard maven project from command-line:

    maven clean package

## Run
Start your server as a simple java application directly from command-line:

    java -jar target/app-rating-0.0.1-SNAPSHOT.jar


## Documentation
You can view the api documentation in swagger-ui by pointing to
http://localhost:8080/swagger-ui.html

## Configuration 
