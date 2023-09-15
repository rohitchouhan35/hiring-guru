package com.rohitchouhan35.hiringmadeeasy.controller;

import com.rohitchouhan35.hiringmadeeasy.model.JobPost;
import com.rohitchouhan35.hiringmadeeasy.repository.JobPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job-posts")
public class JobPostController {

    @Autowired
    private JobPostRepository jobPostRepository;

    @PostMapping
    public JobPost createJobPost(@RequestBody JobPost jobPost) {
        return jobPostRepository.save(jobPost);
    }

    @GetMapping("/{id}")
    public JobPost getJobPost(@PathVariable Long id) {
        return jobPostRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public JobPost updateJobPost(@PathVariable Long id, @RequestBody JobPost updatedJobPost) {
        updatedJobPost.setId(id);
        return jobPostRepository.save(updatedJobPost);
    }

    @DeleteMapping("/{id}")
    public void deleteJobPost(@PathVariable Long id) {
        jobPostRepository.deleteById(id);
    }

    @GetMapping
    public List<JobPost> getAllJobPosts() {
        return jobPostRepository.findAll();
    }
}

