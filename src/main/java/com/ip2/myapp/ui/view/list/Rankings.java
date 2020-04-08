package com.ip2.myapp.ui.view.list;


import com.ip2.myapp.backend.entity.Result;
import com.ip2.myapp.backend.entity.Subject;
import com.ip2.myapp.backend.entity.User;
import com.ip2.myapp.backend.service.ResultService;
import com.ip2.myapp.backend.service.SubjectService;
import com.ip2.myapp.backend.service.UserService;
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

@Route(value="rankings", layout = MainLayout.class)
@PageTitle("Rankings | Integrated Project 2")
@CssImport("./styles/shared-styles.css")
public class Rankings extends VerticalLayout {
    Grid<Result> grid = new Grid<>(Result.class);
    TextField filterText = new TextField();

    private SubjectService subjectService;
    private ResultService resultService;
    private UserService userService;


    public Rankings(SubjectService subjectService, ResultService resultService, UserService userService){
        this.subjectService = subjectService;
        this.resultService = resultService;
        this.userService = userService;
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
        filterText.setPlaceholder("Filter by score...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }


    private  void configureGrid() {
        grid.addClassName("result-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("user");
        grid.removeColumnByKey("subject");
        grid.setColumns("score");
        grid.addColumn(result -> {
            Subject subject = result.getSubject();
            return subject == null ? "-" : subject.getName();
        }).setHeader("Subject");

        grid.addColumn(result -> {
            User user = result.getUser();
            return  user == null ? "-" : user.getUserName();
        }).setHeader("User");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));


    }

    private void updateList() {
        grid.setItems(resultService.findAll(filterText.getValue()));
    }



}
