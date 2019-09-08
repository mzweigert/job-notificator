package com.mzweigert.jobnotifier.repository;

import com.mzweigert.jobnotifier.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {

	Optional<Job> findByUrlAndSourcePageId(@Param("url") String url, @Param("sourcePageId") Long sourcePageId);
}
