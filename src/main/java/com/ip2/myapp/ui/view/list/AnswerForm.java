package com.ip2.myapp.ui.view.list;

import com.ip2.myapp.backend.entity.Answer;
import com.ip2.myapp.backend.entity.Question;
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

public class AnswerForm extends FormLayout {


    TextField answer = new TextField("Answer");
    ComboBox<Question> question = new ComboBox<>("Question");
    ComboBox<Boolean> rightAnswer = new ComboBox<Boolean>("Set to false to set the answer as a false one");
    Button save = new Button("Save");
    Button close = new Button("Cancel");

    Binder<Answer> binder = new BeanValidationBinder<>(Answer.class);

    public AnswerForm(List<Question> questions) {
        addClassName("answer-form");

        binder.bindInstanceFields(this);
        rightAnswer.setItems(false);
        question.setItems(questions);
        question.setItemLabelGenerator(Question::getQuestion);
        add(
                answer,
                rightAnswer,
                question,
                createAnswersLayout()
        );


    }


    public void setAnswer(Answer answer) {
        binder.setBean(answer);
    }


    private Component createAnswersLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, close);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
            addClassName("editing");

        }
    }

    public static abstract class AnswerFormEvent extends ComponentEvent<AnswerForm> {
        private Answer answer;

        protected AnswerFormEvent(AnswerForm source, Answer answer) {
            super(source, false);
            this.answer = answer;
        }

        public Answer getAnswer() {
            return answer;
        }
    }

    public static class SaveEvent extends AnswerFormEvent {
        SaveEvent(AnswerForm source, Answer answer) {
            super(source, answer);
        }
    }

    public static class CloseEvent extends AnswerFormEvent {
        CloseEvent(AnswerForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


}
