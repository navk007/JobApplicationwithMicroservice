package com.navdeep.jobMS.job;

import java.util.List;

import com.navdeep.jobMS.job.dto.JobDTO;

public interface JobService {
    List<JobDTO> findAll();
    void createJob(Job job);
    JobDTO getJobById(Long jobId);
    Boolean deleteById(Long jobId);
    Boolean updateJob(Long jobId, Job job);
}
