package com.rohitchouhan35.hiringmadeeasy.repository;

import com.rohitchouhan35.hiringmadeeasy.model.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
}
