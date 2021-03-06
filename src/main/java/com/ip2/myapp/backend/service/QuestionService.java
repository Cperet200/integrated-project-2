package com.ip2.myapp.backend.service;


import com.ip2.myapp.backend.entity.Answer;
import com.ip2.myapp.backend.entity.Question;
import com.ip2.myapp.backend.entity.Subject;
import com.ip2.myapp.backend.entity.User;
import com.ip2.myapp.backend.repository.AnswerRepository;
import com.ip2.myapp.backend.repository.QuestionRepository;
import com.ip2.myapp.backend.repository.SubjectRepository;
import com.ip2.myapp.backend.repository.UserRepository;
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
    private UserRepository userRepository;

    public QuestionService(QuestionRepository questionRepository,
                           SubjectRepository subjectRepository, AnswerRepository answerRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.subjectRepository = subjectRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
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


        User user = new User("admin", "admin","admin", "password");
        User user2 = new User("test", "test", "user", "password");
        userRepository.save(user);
        userRepository.save(user2);


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
            Question question4 = new Question("What is 2 +2",  subjects.get(0),Question.Difficulty.Medium);

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
            Answer answer13 = new Answer("4", false, question4);
            Answer answer14 = new Answer("23", false, question4);
            Answer answer15 = new Answer("22", true, question4);
            Answer answer16 = new Answer("483297", false, question4);


            List<Answer> question1Answers = new LinkedList<>();
            List<Answer> question2Answers = new LinkedList<>();
            List<Answer> question3Answers = new LinkedList<>();
            List<Answer> question4Answers = new LinkedList<>();

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
            question4Answers.add(answer13);
            question4Answers.add(answer14);
            question4Answers.add(answer15);
            question4Answers.add(answer16);

            answers.addAll(question1Answers);
            answers.addAll(question2Answers);
            answers.addAll(question3Answers);
            answers.addAll(question4Answers);

            question1.setAnswers(question1Answers);
            question2.setAnswers(question2Answers);
            question3.setAnswers(question3Answers);
            question4.setAnswers(question4Answers);

            List<Question> questions  = new LinkedList<>();
            questions.add(question1);
            questions.add(question2);
            questions.add(question3);
            questions.add(question4);
            answerRepository.saveAll(answers);

            questionRepository.saveAll(questions);

        }



    }


}



