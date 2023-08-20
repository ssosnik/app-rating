package com.ssosnik.apprating.domain.repository;

import com.ssosnik.apprating.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.app.appUUID = :appUUID AND r.date BETWEEN :since AND :until")
    Optional<Double> findAverageRatingByAppUUIDAndDateBetween(String appUUID, LocalDate since, LocalDate until);
}
