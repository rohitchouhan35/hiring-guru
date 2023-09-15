package com.rohitchouhan35.hiringmadeeasy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "password_reset_tokens")
@Data
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
