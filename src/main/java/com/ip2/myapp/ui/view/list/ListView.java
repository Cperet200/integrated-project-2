package com.ip2.myapp.ui.view.list;

import com.ip2.myapp.backend.entity.Answer;
import com.ip2.myapp.backend.entity.Question;
import com.ip2.myapp.backend.entity.Subject;
import com.ip2.myapp.backend.service.AnswerService;
import com.ip2.myapp.backend.service.QuestionService;
import com.ip2.myapp.backend.service.SubjectService;
import com.ip2.myapp.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value="", layout = MainLayout.class)
@PageTitle("Questions | Integrated Project 2")
@CssImport("./styles/shared-styles.css")
public class ListView extends VerticalLayout {

    private final QuestionForm questionForm;
    private final AnswerForm answerForm;
    Grid<Question> grid = new Grid<>(Question.class);
    TextField filterText = new TextField();


    private QuestionService questionService;
    private AnswerService answerService;


    public ListView(QuestionService questionService, SubjectService subjectService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();


        questionForm = new QuestionForm(subjectService.findAll());
        questionForm.addListener(QuestionForm.SaveEvent.class, this::saveQuestion);
        questionForm.addListener(QuestionForm.DeleteEvent.class, this::deleteQuestion);
        questionForm.addListener(QuestionForm.CloseEvent.class, e -> closeEditor());

        answerForm = new AnswerForm(questionService.findAll());
        answerForm.addListener(AnswerForm.SaveEvent.class, this::saveAnswer);
        answerForm.addListener(AnswerForm.CloseEvent.class, e -> closeEditor());


        Div content = new Div(grid, questionForm, answerForm);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
        closeEditor();
    }



    private void deleteQuestion(QuestionForm.DeleteEvent evt) {
        questionService.delete(evt.getQuestion());
        updateList();
        closeEditor();
    }

    private void saveQuestion(QuestionForm.SaveEvent evt) {
        questionService.save(evt.getQuestion());
        updateList();
        closeEditor();
    }

    private void saveAnswer(AnswerForm.SaveEvent ans) {
        answerService.save(ans.getAnswer());
        updateList();
        closeEditor();
        ;
    }
    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter by difficulty...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addQuestionButton = new Button("Add question", click -> addQuestion());
        Button addAnswerButton = new Button("Add answer to a question", click -> addAnswer());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addQuestionButton, addAnswerButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addQuestion() {
        grid.asSingleSelect().clear();
        editQuestion(new Question());

    }

    private void addAnswer(){
        grid.asSingleSelect().clear();
        editAnswer(new Answer());

    }

    private void configureGrid() {
        grid.addClassName("question-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("subject");
        grid.removeColumnByKey("answers");
        grid.setColumns("question", "correctAnswer", "difficulty");
        grid.addColumn(question -> {
            Subject subject = question.getSubject();
            return subject == null ? "-" : subject.getName();
        }).setHeader("Subject");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editQuestion(evt.getValue()));
    }

    private void editQuestion(Question question) {
        if (question == null) {
            closeEditor();
        } else {
            questionForm.setQuestion(question);
            questionForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void editAnswer(Answer answer) {
        if (answer == null) {
            closeEditor();
        } else {
            answerForm.setAnswer(answer);
            answerForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        questionForm.setQuestion(null);
        questionForm.setVisible(false);
        answerForm.setAnswer(null);
        answerForm.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(questionService.findAll(filterText.getValue()));
    }

}
