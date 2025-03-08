package com.example.springRest.controller;


import com.example.springRest.model.JobPost;
import com.example.springRest.model.User;
import com.example.springRest.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000")

public class JobRestController {

    @Autowired
    JobService service;

    @GetMapping("jobPosts")
    public List<JobPost> getAllJobs(){
        return service.returnAllJobPosts();
    }


    @GetMapping("/jobPost/{postId}")
    public Object getJob(@PathVariable int postId) {
        JobPost jobPost = service.getJob(postId);

        if (jobPost == null) {
            return "Job post with ID " + postId + " not found";
        }
        return jobPost;
    }


    @PostMapping("jobPost")
    public JobPost addJob(@RequestBody JobPost jobPost){
        service.addJobPost(jobPost);
        return service.getJob(jobPost.getPostId());
    }

    @PutMapping("jobPost")
    public String updateJob(@RequestBody JobPost jobPost) {
        JobPost existingJobPost = service.getJob(jobPost.getPostId());
        if (existingJobPost == null) {

            return "Job post not found with postId: " + jobPost.getPostId();
        }
        service.updateJob(jobPost);

        return "Job post updated successfully with postId: " + jobPost.getPostId();
    }


    @DeleteMapping("jobPost/{postId}")
    public String deleteJob(@PathVariable int postId){
        service.deleteJob(postId);
        return "deleted";
    }

    @GetMapping("load")
    public String loadData() {
        service.load();
        return "success";
    }

    @GetMapping("jobPosts/keyword/{keyword}")
    public Object search(@PathVariable("keyword") String keyword) {
        List<JobPost> results = service.search(keyword);

        if (results == null || results.isEmpty()) {
            return "No job posts found";
        }

        return results;
    }

    @DeleteMapping("/deleteAll")
    public String deleteAll(){
        service.deleteAllJobs();
        return "deleted All Jobs";
    }




}
