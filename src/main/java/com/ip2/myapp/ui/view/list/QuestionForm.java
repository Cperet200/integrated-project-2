package com.ip2.myapp.ui.view.list;

import com.ip2.myapp.backend.entity.Question;
import com.ip2.myapp.backend.entity.Subject;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class QuestionForm extends FormLayout {


    TextField question = new TextField("Question");
    ComboBox<Question.Difficulty> difficulty = new ComboBox<>("Difficulty");
    ComboBox<Subject> subject = new ComboBox<>("Subject");


    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Question> binder = new BeanValidationBinder<>(Question.class);


    public QuestionForm(List<Subject> subjects) {
        addClassName("question-form");
        binder.bindInstanceFields(this);
        difficulty.setItems(Question.Difficulty.values());
        subject.setItems(subjects);
        subject.setItemLabelGenerator(Subject::getName);


        add(
                question,
                difficulty,
                subject,
                createButtonsLayout()
        );


    }


    public void setQuestion(Question question) {
        binder.setBean(question);
    }


    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());

        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }


    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
            addClassName("editing");
        }
    }


    public static abstract class QuestionFormEvent extends ComponentEvent<QuestionForm> {
        private Question question;

        protected QuestionFormEvent(QuestionForm source, Question question) {
            super(source, false);
            this.question = question;
        }

        public Question getQuestion() {
            return question;
        }
    }


    public static class SaveEvent extends QuestionFormEvent {
        SaveEvent(QuestionForm source, Question question) {
            super(source, question);
        }
    }

    public static class DeleteEvent extends QuestionFormEvent {
        DeleteEvent(QuestionForm source, Question question) {
            super(source, question);
        }
    }


    public static class CloseEvent extends QuestionFormEvent {
        CloseEvent(QuestionForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


}


