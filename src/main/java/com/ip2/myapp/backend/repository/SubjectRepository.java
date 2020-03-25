package com.ip2.myapp.backend.repository;

import com.ip2.myapp.backend.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
