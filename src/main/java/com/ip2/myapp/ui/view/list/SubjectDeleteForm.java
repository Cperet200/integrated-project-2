package com.ip2.myapp.ui.view.list;

import com.ip2.myapp.backend.entity.Question;
import com.ip2.myapp.backend.entity.Subject;
import com.ip2.myapp.backend.service.QuestionService;
import com.ip2.myapp.backend.service.SubjectService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class SubjectDeleteForm extends FormLayout {


    ComboBox<Subject> subject = new ComboBox<>("Subject");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    private SubjectService subjectService;
    private QuestionService questionService;


    public SubjectDeleteForm(List<Subject> subjects, SubjectService subjectService, QuestionService questionService) {
        this.subjectService = subjectService;
        this.questionService = questionService;
        addClassName("subject-delete-form");
        subject.setItems(subjects);
        subject.setItemLabelGenerator(Subject::getName);

        add(
                subject,
                createButtonsLayout()
        );
    }

    private Component createButtonsLayout() {
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);


        close.addClickShortcut(Key.ESCAPE);


        delete.addClickListener(click -> deleteSubject(subject.getValue()));
        close.addClickListener(click -> fireEvent(new SubjectDeleteForm.CloseEvent(this)));


        return new HorizontalLayout(delete, close);
    }

    public void deleteSubject(Subject subject) {

        subjectService.delete(subject);

        this.setVisible(false);
    }

    public void deleteQuestionsOfSubject(Subject subject) {

        List<Question> questions = questionService.findAll();

        for (int i = 0; i < questions.size(); i++) {
           if(questions.get(i).getSubject().equals(subject)){
               subjectService.delete(subject);
           }
        }

        deleteSubject(subject);
    }


    public static abstract class SubjectDeleteFormEvent extends ComponentEvent<SubjectDeleteForm> {
        private Subject subject;

        protected SubjectDeleteFormEvent(SubjectDeleteForm source, Subject subject) {
            super(source, false);
            this.subject = subject;
        }

        public Subject getSubject() {
            return subject;
        }
    }

    public static class DeleteEvent extends SubjectDeleteFormEvent {
        DeleteEvent(SubjectDeleteForm source, Subject subject) {
            super(source, subject);
        }

    }

    public static class CloseEvent extends SubjectDeleteFormEvent {
        CloseEvent(SubjectDeleteForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


}
