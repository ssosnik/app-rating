package com.ssosnik.apprating.domain.repository;

import com.ssosnik.apprating.domain.App;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRepository extends JpaRepository<App, Long> {


    boolean existsByAppUUID(String appUUID);

//    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.app.appUUID = :appUUID")
//    Optional<Double> computeAverageRatingByUUID(@Param("appUUID") String appUUID);

}
