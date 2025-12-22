package com.navdeep.jobMS.job;

import java.util.List;
import com.navdeep.jobMS.job.Job;
import com.navdeep.jobMS.job.dto.JobWithCompanyDTO;

public interface JobService {
    List<JobWithCompanyDTO> findAll();
    void createJob(Job job);
    JobWithCompanyDTO getJobById(Long jobId);
    Boolean deleteById(Long jobId);
    Boolean updateJob(Long jobId, Job job);
}
