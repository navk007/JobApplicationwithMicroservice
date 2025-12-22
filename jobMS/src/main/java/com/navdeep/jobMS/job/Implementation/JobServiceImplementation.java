package com.navdeep.jobMS.job.Implementation;

import com.navdeep.jobMS.job.Job;
import com.navdeep.jobMS.job.JobRepository;
import com.navdeep.jobMS.job.JobService;
import com.navdeep.jobMS.job.dto.JobWithCompanyDTO;
import com.navdeep.jobMS.job.external.Company;
import com.navdeep.jobMS.job.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImplementation implements JobService {
    JobRepository jobRepository;

    @Autowired
    RestTemplate restTemplate;

    public JobServiceImplementation(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    private JobWithCompanyDTO convertToDto(Job job){

        Company company = restTemplate.getForObject("http://COMPANYMS:8081/companies/" + job.getCompanyId(), Company.class);
        JobWithCompanyDTO jobWithCompanyDTO=JobMapper.mapToJobWithCompanyDto(job, company);

        return jobWithCompanyDTO;
    }

    @Override
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs=jobRepository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOs = new ArrayList<>();

        for(Job job: jobs){
            JobWithCompanyDTO jobWithCompanyDTO=convertToDto(job);
            jobWithCompanyDTOs.add(jobWithCompanyDTO);
        }

        return jobWithCompanyDTOs;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobWithCompanyDTO getJobById(Long id){
        Job job=jobRepository.findById(id).orElse(null);
        return convertToDto(job);
    }

    @Override
    public Boolean deleteById(Long id){
        Optional<Job> jobOptional=jobRepository.findById(id);
        if(jobOptional.isPresent()){
            jobRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateJob(Long id, Job updatedJob){
        Optional<Job> jobOptional= jobRepository.findById(id);
        if(jobOptional.isPresent()){
            Job job=jobOptional.get();
            job.setDescription(updatedJob.getDescription());
            job.setTitle(updatedJob.getTitle());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            job.setCompany(updatedJob.getCompanyId());
            jobRepository.save(job);
            return true;
        }
        return false;
    }
}
