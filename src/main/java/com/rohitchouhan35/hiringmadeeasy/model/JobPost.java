package com.rohitchouhan35.hiringmadeeasy.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long jobId;
    private String title;
    private String description;
    private String location;
    private Double salary;
    private Date applicationDeadline;
    private String imageUrl;
    private String videoUrl;
    private String requiredQualifications;
    private String requiredSkills;
    private List<Long> applicantList;

}

