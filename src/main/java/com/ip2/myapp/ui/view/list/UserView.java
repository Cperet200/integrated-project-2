package com.ip2.myapp.ui.view.list;

import com.ip2.myapp.backend.entity.Question;
import com.ip2.myapp.backend.entity.Subject;
import com.ip2.myapp.backend.service.QuestionService;
import com.ip2.myapp.ui.MainLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value="user", layout = MainLayout.class)
@PageTitle("Questions | Integrated Project 2")
@CssImport("./styles/shared-styles.css")
public class UserView extends VerticalLayout {


    Grid<Question> grid = new Grid<>(Question.class);
    TextField filterText = new TextField();


    private QuestionService questionService;



    public UserView(QuestionService questionService) {
        this.questionService = questionService;

        addClassName("list-view");
        setSizeFull();
        configureGrid();


        Div content = new Div(grid);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
    }





    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter by difficulty...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());



        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void configureGrid() {
        grid.addClassName("question-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("subject");
        grid.removeColumnByKey("answers");
        grid.setColumns("question", "difficulty");
        grid.addColumn(question -> {
            Subject subject = question.getSubject();
            return subject == null ? "-" : subject.getName();
        }).setHeader("Subject");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }




    private void updateList() {
        grid.setItems(questionService.findAll(filterText.getValue()));
    }

}
