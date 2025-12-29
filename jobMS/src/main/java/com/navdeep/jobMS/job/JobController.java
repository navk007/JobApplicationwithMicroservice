package com.navdeep.jobMS.job;

import com.navdeep.jobMS.job.dto.JobDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<JobDTO>> firstAll(){
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
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id){
        JobDTO jobDTO =jobService.getJobById(id);

        if(jobDTO != null) return new ResponseEntity<>(jobDTO,HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //  Delete a Job with JobID: id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id){
        Boolean isDeleted=jobService.deleteById(id);

        if(isDeleted) return new ResponseEntity<>("Job Deleted", HttpStatus.OK);
        else return new ResponseEntity<>("Job not Found", HttpStatus.NOT_FOUND);
    }

//    Update a Job with JobID: id
//    @PutMapping("/{id}")
//    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job updatedJob){
//        Boolean isUpdated=jobService.updateJob(id, updatedJob);
//
//        if(isUpdated) return new ResponseEntity<>(jobService.getJobById(id), HttpStatus.OK);
//        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
}