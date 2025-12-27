package com.navdeep.jobMS.job.mapper;

import com.navdeep.jobMS.job.Job;
import com.navdeep.jobMS.job.dto.JobDTO;
import com.navdeep.jobMS.job.external.Company;
import com.navdeep.jobMS.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO mapToJobWithCompanyDto(Job job, Company company, List<Review> reviews) {

        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setCompany(company);
        jobDTO.setReview(reviews);

        return jobDTO;
    }
}
