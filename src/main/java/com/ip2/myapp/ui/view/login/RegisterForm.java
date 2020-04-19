package com.ip2.myapp.ui.view.login;

import com.ip2.myapp.backend.entity.User;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class RegisterForm extends FormLayout {


    private Text text = new Text("Error, you have selected a User name that already exists, please select another name");

    List<User> users;
    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");
    TextField userName = new TextField("User name");
    PasswordField password = new PasswordField("Password");
    Button save = new Button("Save");
    Button close = new Button("Cancel");

    Binder<User> binder = new BeanValidationBinder<>(User.class);


    public RegisterForm(List<User> users) {
        this.users = users;

        addClassName("question-form");

        binder.bindInstanceFields(this);

        add(
                firstName,
                lastName,
                userName,
                password,
                createRegisterLayout()
        );
    }

    private Component createRegisterLayout(){
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validate());
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, close);
    }


    private void validate(){

        for(int i =0; i < users.size(); i++) {
            if (userName.getValue().toUpperCase().equals(users.get(i).getUserName().toUpperCase())){
                System.out.println(userName.getValue());
                System.out.println(users.get(i).getUserName());
                add(text);
                break;
            } else if((i + 1) == users.size()){
                save();
            }


        }


    }

    private void save() {

        if (binder.isValid()) {


            fireEvent(new SaveEvent(this, binder.getBean()));
            addClassName("editing");

        }
    }




    public static abstract class RegisterFormEvent extends ComponentEvent<RegisterForm> {
        private User user;

        protected RegisterFormEvent(RegisterForm source, User user) {
            super(source, false);
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    public void setUser(User user) {
        binder.setBean(user);
    }

    public static class SaveEvent extends RegisterFormEvent {
        SaveEvent(RegisterForm source, User user) {
            super(source, user);
        }
    }

    public static class CloseEvent extends RegisterFormEvent {
        CloseEvent(RegisterForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }



}
