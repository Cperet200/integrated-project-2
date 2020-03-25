package com.ip2.myapp.backend.service;


import com.ip2.myapp.backend.entity.Subject;
import com.ip2.myapp.backend.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubjectService {

    private SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository ) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public Map<String, Integer> getStats() {
        HashMap<String, Integer> stats = new HashMap<>();
        findAll().forEach(subject -> stats.put(subject.getName(), subject.getQuestions().size()));
        return stats;
    }

}
