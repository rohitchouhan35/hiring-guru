package com.rohitchouhan35.hiringmadeeasy.repository;

import com.rohitchouhan35.hiringmadeeasy.model.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {
}
