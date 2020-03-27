package com.ip2.myapp.ui.view.quiz;

import com.ip2.myapp.backend.entity.Answer;
import com.ip2.myapp.backend.entity.Question;
import com.ip2.myapp.backend.service.AnswerService;
import com.ip2.myapp.backend.service.QuestionService;
import com.ip2.myapp.backend.service.SubjectService;
import com.ip2.myapp.ui.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;


@Route(value = "quiz", layout = MainLayout.class) //
@PageTitle("Quiz | Integrated Project 2")
public class QuizView extends VerticalLayout {


    private QuestionService questionService;
    private SubjectService subjectService;
    private AnswerService answerService;


    String answer;
    TextArea textArea;
    ComboBox<Answer> answers = new ComboBox<>("Answers");
    List<Question> questions;


    public QuizView(QuestionService questionService, SubjectService subjectService, AnswerService answerService) {
        this.questionService = questionService;
        this.subjectService = subjectService;
        this.answerService = answerService;
        questions = questionService.findAll();

        textArea = new TextArea(questions.get(0).getQuestion());
        textArea.setPlaceholder("Type in your answer here");

        add(textArea, createAnswerPanel());


    }

    private Component createAnswerPanel() {
        Button answer = new Button(questions.get(0).getQuestion());
        answer.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        answer.addClickListener(click -> getAnswer(questions.get(0)));
        questions = questionService.findAll();
        answers.setItems(getAnswersFromQuestions(questions.get(0)));
        return new HorizontalLayout(answer, answers);
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

    private void getAnswer(Question question) {


        if (answers.getValue().getQuestion().getId() == question.getId()) {
            answer = "correct";
        } else {
            answer = "incorrect";
        }
        System.out.println(answers.getValue().getQuestion().getId());
        System.out.println(question.getId());
        System.out.println(answer);
    }

}
