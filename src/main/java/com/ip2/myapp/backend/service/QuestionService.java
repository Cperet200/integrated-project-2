package com.ip2.myapp.backend.service;


import com.ip2.myapp.backend.entity.Answer;
import com.ip2.myapp.backend.entity.Question;
import com.ip2.myapp.backend.entity.Subject;
import com.ip2.myapp.backend.repository.AnswerRepository;
import com.ip2.myapp.backend.repository.QuestionRepository;
import com.ip2.myapp.backend.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class QuestionService {
    private static final Logger LOGGER = Logger.getLogger(QuestionService.class.getName());
    private QuestionRepository questionRepository;
    private SubjectRepository subjectRepository;
    private AnswerRepository answerRepository;

    public QuestionService(QuestionRepository questionRepository,
                           SubjectRepository subjectRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.subjectRepository = subjectRepository;
        this.answerRepository = answerRepository;
    }


    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public List<Question> findAll(String stringFilter){
        if (stringFilter == null ||stringFilter.isEmpty()) {
            return questionRepository.findAll();
        } else {
            return questionRepository.search(stringFilter);
        }
    }



    public long count() {
        return questionRepository.count();
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }

    public void save(Question question) {
        if (question == null) {
            LOGGER.log(Level.SEVERE,
                    "Question is null. Are you sure you have connected your form to the application?");
            return;
        }
        questionRepository.save(question);
    }

    @PostConstruct
    public void populateTestData() {
        if (subjectRepository.count() == 0) {
            subjectRepository.saveAll(
                    Stream.of("DataStructures", "Programming", "Databases")
                            .map(Subject::new)
                            .collect(Collectors.toList()));
        }

        if (questionRepository.count() == 0) {
            List<Subject> subjects = subjectRepository.findAll();
            Question question1 = new Question("What is 4 + 4",subjects.get(0), Question.Difficulty.Easy);
            Question question2 = new Question("What language was named after a coffee bean",  subjects.get(1), Question.Difficulty.Easy);
            Question question3 = new Question("What sql command do you use to change the data within a database",  subjects.get(2),Question.Difficulty.Medium);

            Answer answer1 = new Answer("4", false, question1);
            Answer answer2 = new Answer("76", false, question1);
            Answer answer3 = new Answer("8", true, question1);
            Answer answer4 = new Answer("5", false, question1);
            Answer answer5 = new Answer("Java", true, question2);
            Answer answer6 = new Answer("Python", false, question2);
            Answer answer7 = new Answer("C++", false, question2);
            Answer answer8 = new Answer("Ruby", false, question2);
            Answer answer9 = new Answer("Insert", false, question3);
            Answer answer10 = new Answer("Select", false, question3);
            Answer answer11 = new Answer("Alter", true, question3);
            Answer answer12 = new Answer("Drop", false, question3);


            List<Answer> question1Answers = new LinkedList<>();
            List<Answer> question2Answers = new LinkedList<>();
            List<Answer> question3Answers = new LinkedList<>();

            List<Answer> answers = new LinkedList<>();



            question1Answers.add(answer1);
            question1Answers.add(answer2);
            question1Answers.add(answer3);
            question1Answers.add(answer4);
            question2Answers.add(answer5);
            question2Answers.add(answer6);
            question2Answers.add(answer7);
            question2Answers.add(answer8);
            question3Answers.add(answer9);
            question3Answers.add(answer10);
            question3Answers.add(answer11);
            question3Answers.add(answer12);

            answers.addAll(question1Answers);
            answers.addAll(question2Answers);
            answers.addAll(question3Answers);



            question1.setCorrectAnswer(answer3.getAnswer());
            question2.setCorrectAnswer(answer5.getAnswer());
            question3.setCorrectAnswer(answer1.getAnswer());

            question1.setAnswers(question1Answers);
            question2.setAnswers(question2Answers);
            question3.setAnswers(question3Answers);

            List<Question> questions  = new LinkedList<>();
            questions.add(question1);
            questions.add(question2);
            questions.add(question3);
            answerRepository.saveAll(answers);

            questionRepository.saveAll(questions);

        }



    }

    private List<Question> getQuestions(){
        return questionRepository.findAll();
    }


}



