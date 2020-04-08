package com.ip2.myapp.ui;


import com.ip2.myapp.ui.view.dashboard.DashboardView;
import com.ip2.myapp.ui.view.list.ListView;
import com.ip2.myapp.ui.view.list.Rankings;
import com.ip2.myapp.ui.view.quiz.QuizView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();


    }

    private void createHeader() {

        H1 logo = new H1("Integrated Project 2");
        logo.addClassName("logo");

        Anchor logout = new Anchor("/login", "Log out");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");
        header.expand(logo);

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("List", ListView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());
        RouterLink dashboardLink = new RouterLink("Dashboard", DashboardView.class);
        RouterLink quizLink = new RouterLink("Quiz", QuizView.class);
        RouterLink resultLink = new RouterLink("Rankings", Rankings.class);
        addToDrawer(new VerticalLayout(listLink, dashboardLink, quizLink, resultLink));


    }


}



