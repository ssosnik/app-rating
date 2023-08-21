package com.ssosnik.apprating.domain.repository;

import com.ssosnik.apprating.domain.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AppRepository extends JpaRepository<App, Long> {


    boolean existsByAppUUID(String appUUID);

    App findByAppUUID(String appUUID);

    @Query("SELECT a, AVG(r.rating) as avgRating " +
            "FROM App a JOIN a.reviews r " +
            "WHERE r.date BETWEEN :since AND :until " +
            "AND r.reviewerAge BETWEEN :fromAge AND :toAge " +
            "GROUP BY a " +
            "ORDER BY avgRating DESC")
    List<Object[]> findTopAppsByAverageRating(@Param("since") LocalDate since,
                                              @Param("until") LocalDate until,
                                              @Param("fromAge") int fromAge,
                                              @Param("toAge") int toAge);


//    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.app.appUUID = :appUUID")
//    Optional<Double> computeAverageRatingByUUID(@Param("appUUID") String appUUID);

}
