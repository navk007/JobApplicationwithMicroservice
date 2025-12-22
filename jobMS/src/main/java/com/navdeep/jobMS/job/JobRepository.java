package com.navdeep.jobMS.job;

import org.springframework.data.jpa.repository.JpaRepository;
import com.navdeep.jobMS.job.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
}
