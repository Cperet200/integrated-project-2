package com.ip2.myapp.ui.view.list;

import com.ip2.myapp.backend.entity.Subject;
import com.ip2.myapp.backend.service.SubjectService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;

public class SubjectForm extends FormLayout {
    TextField subjectName = new TextField("Subject name");
    private SubjectService subjectService;


    Button save = new Button("Save");

    Subject subject;

    public SubjectForm(SubjectService subjectService) {
        this.subjectService = subjectService;
        addClassName("subject-form");



        add(subjectName, createSubjectLayout());

    }

    private Component createSubjectLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);

        save.addClickListener(click -> validateAndSave());
        return new HorizontalLayout(save);
    }

    private void validateAndSave() {
        System.out.println(subjectName.getValue());
        subject = new Subject(subjectName.getValue());
        subjectService.save(subject);
        this.setVisible(false);
    }

    public static abstract class SubjectFormEvent extends ComponentEvent<SubjectForm> {
        private Subject subject;

        protected SubjectFormEvent(SubjectForm source, Subject subject){
            super(source, false);
            this.subject = subject;
        }

        public Subject getSubject() {return  subject; }
    }

    public static class SaveEvent extends SubjectFormEvent {
        SaveEvent(SubjectForm source, Subject subject) {
            super(source, subject); }

        }
        public <T extends ComponentEvent<?>>Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
            return getEventBus().addListener(eventType, listener);
    }

}
