package com.ip2.myapp.ui.view.dashboard;


import com.ip2.myapp.backend.service.QuestionService;
import com.ip2.myapp.backend.service.SubjectService;
import com.ip2.myapp.ui.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Map;

@Route(value = "dashboard", layout = MainLayout.class) //
@PageTitle("Dashboard | Integrated Project 2")
public class DashboardView extends VerticalLayout {

    private QuestionService questionService;
    private SubjectService subjectService;

    public DashboardView(QuestionService questionService, SubjectService subjectService) {
        this.questionService = questionService;
        this.subjectService = subjectService;

        add(getQuestionStats(), getSubjectsChart());
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    private Component getQuestionStats() {
        Span stats = new Span(questionService.count() + " questions");
        stats.addClassName("questions-stats");
        return stats;
    }

    private Chart getSubjectsChart() {
        Chart chart = new Chart(ChartType.PIE);

        DataSeries dataSeries = new DataSeries();
        Map<String, Integer> subjects = subjectService.getStats();
        subjects.forEach((subject, questions) -> dataSeries.add(new DataSeriesItem(subject, questions)));
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }


}
