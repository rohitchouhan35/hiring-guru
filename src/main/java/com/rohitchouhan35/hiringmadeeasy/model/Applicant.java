package com.rohitchouhan35.hiringmadeeasy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String resumeUrl;
    private String coverLetter;
    private Date applicationDate;
    private ApplicationStatus status;

    @ManyToOne
    @JoinColumn(name = "job_post_id")
    private JobPost jobPost;

}
