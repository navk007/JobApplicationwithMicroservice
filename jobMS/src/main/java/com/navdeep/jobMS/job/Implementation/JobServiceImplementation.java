package com.navdeep.jobMS.job.Implementation;

import com.navdeep.jobMS.job.Job;
import com.navdeep.jobMS.job.JobRepository;
import com.navdeep.jobMS.job.JobService;
import com.navdeep.jobMS.job.dto.JobDTO;
import com.navdeep.jobMS.job.external.Company;
import com.navdeep.jobMS.job.external.Review;
import com.navdeep.jobMS.job.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    private JobDTO convertToDto(Job job){

        Company company = restTemplate.getForObject("http://COMPANYMS:8081/companies/" + job.getCompanyId(), Company.class);
        ResponseEntity<List<Review>> reviewResponse=restTemplate.exchange(
                "http://REVIEWMS:8083/reviews?companyId=" + job.getCompanyId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Review>>(){
                });
        List<Review> reviews=reviewResponse.getBody();

        JobDTO jobDTO =JobMapper.mapToJobWithCompanyDto(job, company, reviews);

        return jobDTO;
    }

    @Override
    public List<JobDTO> findAll() {
        List<Job> jobs=jobRepository.findAll();
        List<JobDTO> jobDTOS = new ArrayList<>();

        for(Job job: jobs){
            JobDTO jobDTO =convertToDto(job);
            jobDTOS.add(jobDTO);
        }

        return jobDTOS;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id){
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
