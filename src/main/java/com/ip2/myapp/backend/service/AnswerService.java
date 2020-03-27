package com.ip2.myapp.backend.service;


import com.ip2.myapp.backend.entity.Answer;
import com.ip2.myapp.backend.repository.AnswerRepository;
import com.ip2.myapp.backend.repository.QuestionRepository;
import com.ip2.myapp.backend.repository.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AnswerService {

    private QuestionRepository questionRepository;
    private SubjectRepository subjectRepository;
    private AnswerRepository answerRepository;

    private static final Logger LOGGER = Logger.getLogger(AnswerService.class.getName());

    public AnswerService (QuestionRepository questionRepository,
                          SubjectRepository subjectRepository, AnswerRepository answerRepository){
        this.questionRepository = questionRepository;
        this.subjectRepository = subjectRepository;
        this.answerRepository = answerRepository;

    }

    public List<Answer> findAll() {
        return answerRepository.findAll();
    }

    @Transactional
    public void save(Answer answer) {
        if (answer == null) {
            LOGGER.log(Level.SEVERE,
                    "Answer is null. Are you sure you have connected your form to the application?");
            return;
        }
        answer.setQuestion(questionRepository.findById(answer.getQuestion().getId()).get());
        answerRepository.save(answer);
    }


}
