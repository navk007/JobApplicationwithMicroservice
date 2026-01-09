package com.navdeep.jobMS.job.Implementation;

import com.navdeep.jobMS.job.Job;
import com.navdeep.jobMS.job.JobRepository;
import com.navdeep.jobMS.job.JobService;
import com.navdeep.jobMS.job.clients.CompanyClient;
import com.navdeep.jobMS.job.clients.ReviewClient;
import com.navdeep.jobMS.job.dto.JobDTO;
import com.navdeep.jobMS.job.external.Company;
import com.navdeep.jobMS.job.external.Review;
import com.navdeep.jobMS.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImplementation implements JobService {
    JobRepository jobRepository;

    private CompanyClient companyClient;
    private ReviewClient reviewClient;
    Integer attempt=1;

    @Autowired
    RestTemplate restTemplate;

    public JobServiceImplementation(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    private JobDTO convertToDto(Job job) {
//        Before OpenFeignCleint Integration
//        Company company = restTemplate.getForObject("http://COMPANYMS:8081/companies/" + job.getCompanyId(), Company.class);
//        ResponseEntity<List<Review>> reviewResponse=restTemplate.exchange(
//                "http://REVIEWMS:8083/reviews?companyId=" + job.getCompanyId(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Review>>(){
//                });
//        List<Review> reviews=reviewResponse.getBody();

//        After OpenFeignCleint Integration
        Company company = companyClient.getCompany(job.getCompanyId());
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

        JobDTO jobDTO = JobMapper.mapToJobWithCompanyDto(job, company, reviews);

        return jobDTO;
    }

    public List<String> circuitBreakerFallback(Exception ex){
        List<String> list=new ArrayList<>();
        list.add("Dummy");

        return list;
    }

//    public String rateLimitFallback(Exception e) {
//        return "Too many requests! Please wait and try again later.";
//    }

    public List<String> rateLimitFallback(Exception ex){
        List<String> list=new ArrayList<>();
        list.add("Rate Limit Exceeded");

        return list;
    }


    @Override
//    @CircuitBreaker(name="jobBreaker", fallbackMethod = "jobBreakerFallback")
//    @Retry(name="jobBreaker", fallbackMethod = "circuitBreakerFallback")
    @RateLimiter(name = "jobRateLimitor", fallbackMethod = "rateLimitFallback")
    public List<JobDTO> findAll() {
        System.out.println("Attempts: " + attempt++);
        List<Job> jobs=jobRepository.findAll();
        List<JobDTO> jobDTOS = new ArrayList<>();

        for(Job job: jobs){
            System.out.println(job.getCompanyId());
            if(job.getCompanyId()!=null){
                JobDTO jobDTO=convertToDto(job);
                jobDTOS.add(jobDTO);
            }
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
