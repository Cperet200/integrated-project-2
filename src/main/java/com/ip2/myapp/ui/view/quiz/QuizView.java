package com.ip2.myapp.ui.view.quiz;

import com.ip2.myapp.backend.entity.Answer;
import com.ip2.myapp.backend.entity.Question;
import com.ip2.myapp.backend.entity.Result;
import com.ip2.myapp.backend.entity.Subject;
import com.ip2.myapp.backend.service.AnswerService;
import com.ip2.myapp.backend.service.QuestionService;
import com.ip2.myapp.backend.service.ResultService;
import com.ip2.myapp.backend.service.SubjectService;
import com.ip2.myapp.ui.MainLayout;
import com.ip2.myapp.ui.view.login.LoginView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Route(value = "quiz", layout = MainLayout.class) //
@PageTitle("Quiz | Integrated Project 2")
public class QuizView extends VerticalLayout {


    private QuestionService questionService;
    private SubjectService subjectService;
    private AnswerService answerService;
    private ResultService resultService;



    ComboBox<Answer> answers = new ComboBox<>("Answer");
    ComboBox<Subject> subjects = new ComboBox<>("Subject");
    Button subjectPick = new Button("Select Subject");
    Button answer;
    Text scoreRes = new Text("");
    Text text = new Text("");
    Subject currentSubject;


    String testAnswer;
    List<Question> questions;
    int count = 0;

    double score = 0;
    public QuizView(QuestionService questionService, SubjectService subjectService, AnswerService answerService, ResultService resultService) {
        this.questionService = questionService;
        this.subjectService = subjectService;
        this.answerService = answerService;
        this.resultService = resultService;


        add(new VerticalLayout(selectSubject()));


    }

    private Component selectSubject() {
        subjects.setItems(subjectService.findAll());


        subjectPick.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        subjectPick.addClickListener(click -> currentSubject = subjects.getValue());
        subjectPick.addClickListener(click -> add(createAnswerPanel(subjects.getValue())));
        subjectPick.addClickListener(click -> selectSubject().setVisible(false));
        subjectPick.addClickListener(click -> subjectPick.setVisible(false));


        return new VerticalLayout(subjects, subjectPick);

    }

    private Component createAnswerPanel(Subject subject) {

        if (count < subject.getQuestions().size()) {
            count++;
            return generateQuestion(subject, count - 1);

        } else{
            System.out.println("finished quiz");
            System.out.println(LoginView.curentUser.getUserName() + LoginView.curentUser.getPassword());
            System.out.println(currentSubject.getName());
            System.out.println(score);
            score = ((score* 100) /subject.getQuestions().size());
            Result result = new Result(score, LoginView.curentUser, currentSubject);
            resultService.save(result);
            scoreRes.setText(" Quiz is finished, You scored " + score + "%");
            return (scoreRes);
        }

    }

    private Component generateQuestion(Subject subject, int i) {

        answer = new Button("Answer Question");
        answer.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        questions = subject.getQuestions();
        Collections.shuffle(questions);
        text.setText(questions.get(i).getQuestion());
        answers.setPlaceholder(questions.get(i).getQuestion());
        answers.setItems(getAnswersFromQuestions(questions.get(i)));
        answer.addClickListener(click -> getAnswer(subject));
        answer.addClickListener(click -> answer.setVisible(false));


        return new VerticalLayout(text, answers, answer);
    }


    private List<Answer> getAnswersFromQuestions(Question question) {
        List<Answer> answers = new ArrayList<>();
        List<Answer> finalAnswers = new ArrayList<>();
        answers.addAll(answerService.findAll());
        int i = 0;

        while (i < answers.size()) {
            if (answers.get(i).getQuestion().getId() == question.getId()) {
                finalAnswers.add(answers.get(i));
                System.out.println(answers.get(i).getQuestion());
            }
            i++;


        }

        return finalAnswers;
    }

    private void getAnswer(Subject subject) {

        if (answers.getValue().isRightAnswer() == true) {
            testAnswer = "correct";
            score +=1;
        } else {
            testAnswer = "incorrect";
        }
        add(createAnswerPanel(subject));
        System.out.println(testAnswer);

    }

}
