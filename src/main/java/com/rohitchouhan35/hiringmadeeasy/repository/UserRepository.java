package com.rohitchouhan35.hiringmadeeasy.repository;

import com.rohitchouhan35.hiringmadeeasy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
