package com.ip2.myapp.ui.view.list;

import com.ip2.myapp.backend.entity.Subject;
import com.ip2.myapp.backend.service.SubjectService;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class SubjectForm extends FormLayout {
    TextField subjectName = new TextField("Subject name");
    private SubjectService subjectService;
    private Text text = new Text("Error, you have selected a subject that already exists, please select another name");
    private List<Subject> subjects;


    Button save = new Button("Save");

    Subject subject;

    public SubjectForm(SubjectService subjectService, List<Subject> subjects) {
        this.subjectService = subjectService;
        this.subjects = subjects;
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

        for(int i =0; i < subjects.size(); i++) {
            if (subjectName.getValue().toUpperCase().equals(subjects.get(i).getName().toUpperCase())){
                System.out.println(subjectName.getValue());
                System.out.println(subjects.get(i));
                add(text);
                break;
            } else if((i + 1) == subjects.size()){
                save();
            }


        }



    }

    private void save(){
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
