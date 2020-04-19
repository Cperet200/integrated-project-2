package com.ip2.myapp.ui.view.login;

import com.ip2.myapp.backend.entity.User;
import com.ip2.myapp.backend.repository.UserRepository;
import com.ip2.myapp.backend.service.UserService;
import com.ip2.myapp.ui.view.list.ListView;
import com.ip2.myapp.ui.view.list.UserView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route(value = "login")
@PageTitle("Login")
@NpmPackage(value = "@polymer/iron-form", version = "3.0.1")
@JsModule("@polymer/iron-form/iron-form.js")
public class LoginView extends VerticalLayout {


    private final RegisterForm registerForm;
    private UserService userService;

    public static User curentUser;
    TextField userNameTextField = new TextField();
    PasswordField passwordField = new PasswordField();
    UserRepository userRepository;
    Button addAnswerButton = new Button("Register an account", click -> addUser());
    Text text = new Text("Error, incorrect username or password please try again");


    public LoginView(UserService userService) {
        this.userService = userService;
        registerForm = new RegisterForm(userService.findAll());
        registerForm.addListener(RegisterForm.SaveEvent.class, this::saveUser);
        registerForm.addListener(RegisterForm.CloseEvent.class, e -> closeEditor());
        registerForm.setVisible(false);


        Div content = new Div(registerForm);
        add(content);
        generateLogin();
    }

    public void generateLogin() {

        userNameTextField.getElement().setAttribute("name", "username");
        userNameTextField.setId("username");

        passwordField.getElement().setAttribute("name", "password");
        Button submitButton = new Button("Login");
        submitButton.setId("submitbutton");
        submitButton.addClickListener(buttonClickEvent -> System.out.println(userNameTextField.getValue()));
        submitButton.addClickListener(buttonClickEvent -> checkUserDetails());
        //UI.getCurrent().getPage().executeJs("document.getElementById('submitbutton').addEventListener('click', () => document.getElementById('ironform').submit());");

        FormLayout formLayout = new FormLayout();
        formLayout.add(userNameTextField, passwordField, submitButton, addAnswerButton);

        Element formElement = new Element("form");
        formElement.setAttribute("method", "post");
        formElement.setAttribute("action", "login");
        formElement.appendChild(formLayout.getElement());

        Element ironForm = new Element("iron-form");
        ironForm.setAttribute("id", "ironform");
        ironForm.setAttribute("allow-redirect", true);
        ironForm.appendChild(formElement);
        getElement().appendChild(ironForm);

        setClassName("login-view");


    }

    private void checkPassword(int i) {
        if (passwordField.getValue().equals(userService.findAll().get(i).getPassword())) {
            getHomePage();
        } else {
            printErrorMsg();

        }
    }

    private void saveUser(RegisterForm.SaveEvent evt) {
        userService.save(evt.getUser());
        closeEditor();
    }


    private void addUser() {
        editUser(new User());
    }

    private void editUser(User user) {

        registerForm.setUser(user);
        registerForm.setVisible(true);
        addClassName("editing");

    }

    private void closeEditor() {
        registerForm.setUser(null);
        registerForm.setVisible(false);

        removeClassName("editing");
    }


    private void checkUserDetails() {

        for (int i = 0; i < userService.findAll().size(); i++) {
            if (userNameTextField.getValue().equals(userService.findAll().get(i).getUserName())) {
                setUserInfo(i);
                checkPassword(i);
                break;
            } else if ((i + 1) == userService.findAll().size()) {
                printErrorMsg();

            }
        }

    }

    private void setUserInfo(int i){
       curentUser = userService.findAll().get(i);
       System.out.println(curentUser.getUserName());
    }

    public static User getUserInfo(){
        return curentUser;
    }

    private void printErrorMsg() {

        add(text);
    }

    private void getHomePage() {

        if(curentUser.getUserName().equals("admin")){
            RouterLink listLink = new RouterLink("Successfully Logged in, go to application", ListView.class);
            removeAll();
            add(listLink);
        } else {
            RouterLink listLink = new RouterLink("Successfully Logged in, go to application", UserView.class);
            removeAll();
            add(listLink);
        }

    }


}

