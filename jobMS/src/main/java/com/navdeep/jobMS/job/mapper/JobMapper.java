package com.navdeep.jobMS.job.mapper;

import com.navdeep.jobMS.job.Job;
import com.navdeep.jobMS.job.dto.JobWithCompanyDTO;
import com.navdeep.jobMS.job.external.Company;

public class JobMapper {
    public static JobWithCompanyDTO mapToJobWithCompanyDto(Job job, Company company) {

        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        jobWithCompanyDTO.setId(job.getId());
        jobWithCompanyDTO.setTitle(job.getTitle());
        jobWithCompanyDTO.setDescription(job.getDescription());
        jobWithCompanyDTO.setLocation(job.getLocation());
        jobWithCompanyDTO.setMaxSalary(job.getMaxSalary());
        jobWithCompanyDTO.setMinSalary(job.getMinSalary());
        jobWithCompanyDTO.setCompany(company);

        return jobWithCompanyDTO;
    }
}
