package com.rohitchouhan35.hiringmadeeasy.service;

import com.rohitchouhan35.hiringmadeeasy.model.Applicant;

import java.util.List;
import java.util.Optional;

public interface ApplicantService {

    public List<Applicant> getAllApplicants();
    public Optional<Applicant> getApplicantById(Long id);
    public Applicant createApplicant(Applicant applicant);
    public Applicant updateApplicant(Long id, Applicant updatedApplicant);
    public void deleteApplicant(Long id);

}

