package com.navdeep.jobMS.job.dto;

import com.navdeep.jobMS.job.Job;
import com.navdeep.jobMS.job.external.Company;

public class JobWithCompanyDTO {
    private Job job;
    private Company company;

    public JobWithCompanyDTO() {
    }

    public JobWithCompanyDTO(Job job, Company company) {
        this.job = job;
        this.company = company;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
