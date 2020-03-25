package com.ip2.myapp.backend.repository;

import com.ip2.myapp.backend.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository  extends JpaRepository<Answer, Long> {

}
