package com.ip2.myapp.ui.view.quiz;

import com.ip2.myapp.backend.entity.Question;
import com.ip2.myapp.backend.service.QuestionService;
import com.ip2.myapp.backend.service.SubjectService;
import com.ip2.myapp.ui.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;


@Route(value = "quiz", layout = MainLayout.class) //
@PageTitle("Quiz | Integrated Project 2")
public class QuizView extends VerticalLayout {


    private QuestionService questionService;
    private SubjectService subjectService;



    String answer;
    TextArea textArea;

    public QuizView(QuestionService questionService, SubjectService subjectService){
        this.questionService = questionService;
        this.subjectService = subjectService;

        List<Question> questions = questionService.findAll();

        String question;
        question = questions.get(0).getQuestion();
        textArea = new TextArea(question);
        textArea.setPlaceholder("Type in your answer here");



        add(textArea, createAnswerButton());


    }

    private Component createAnswerButton(){
        Button answer = new Button("Answer");
        answer.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        answer.addClickListener(click -> getAnswer());

        return new HorizontalLayout(answer);
    }

    private void getAnswer(){
        answer = textArea.getValue();

        if(textArea.getValue().toLowerCase().equals(questionService.findAll().get(0).getCorrectAnswer())){
            answer = "correct";
        } else{
            answer = "incorrect";
        }
        Text text = new Text(answer);
        add(text);
        System.out.println(answer);
    }

}
