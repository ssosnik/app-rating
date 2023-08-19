package com.ssosnik.apprating.domain.repository;

import com.ssosnik.apprating.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
