package com.ip2.myapp.backend.service;

import com.ip2.myapp.backend.entity.Result;
import com.ip2.myapp.backend.repository.ResultRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResultService {

    private ResultRepository resultRepository;

    public ResultService(ResultRepository resultRepository ) {
        this.resultRepository = resultRepository;
    }

    public List<Result> findAll() {
        return resultRepository.findAll();
    }

    public List<Result> findAll(String stringFilter) {
        if (stringFilter == null ||stringFilter.isEmpty()) {
            return resultRepository.findAll();
        } else {
            return resultRepository.search(stringFilter);
        }
    }

    @Transactional(noRollbackFor = Exception.class)
    public void save(Result result){
        resultRepository.save(result);
    }

}
