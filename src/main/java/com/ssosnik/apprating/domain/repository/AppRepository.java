package com.ssosnik.apprating.domain.repository;

import com.ssosnik.apprating.domain.App;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRepository extends JpaRepository<App, Long> {
}
