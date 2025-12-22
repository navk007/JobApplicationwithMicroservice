package com.navdeep.jobMS.job;

import com.navdeep.jobMS.job.dto.JobWithCompanyDTO;
import com.navdeep.jobMS.job.external.Company;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.navdeep.jobMS.job.Job;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private JobService jobService;
    private JobRepository jobRepository;

    public JobController(JobService jobService){
        this.jobService=jobService;
    }
    // Get all jobs
    @GetMapping
    public ResponseEntity<List<JobWithCompanyDTO>> firstAll(){
        return new ResponseEntity<>(jobService.findAll(), HttpStatus.OK);
    }

    // Post a job to the Job list
    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job){
        jobService.createJob(job);
        return new ResponseEntity<>("Job create Successfully", HttpStatus.OK);
    }

    // Get specific Job with JobID: id
    @GetMapping("/{id}")
    public ResponseEntity<JobWithCompanyDTO> getJobById(@PathVariable Long id){
        JobWithCompanyDTO jobWithCompanyDTO=jobService.getJobById(id);

        if(jobWithCompanyDTO != null) return new ResponseEntity<>(jobWithCompanyDTO,HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id){
        Boolean isDeleted=jobService.deleteById(id);

        if(isDeleted) return new ResponseEntity<>("Job Deleted", HttpStatus.OK);
        else return new ResponseEntity<>("Job not Found", HttpStatus.NOT_FOUND);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job updatedJob){
//        Boolean isUpdated=jobService.updateJob(id, updatedJob);
//
//        if(isUpdated) return new ResponseEntity<>(jobService.getJobById(id), HttpStatus.OK);
//        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
}