package com.ip2.myapp.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;


@Entity
public class Question extends AbstractEntity implements Cloneable {

    public enum Difficulty {
        Easy, Medium, Hard
    }

    public Question() {}

    public Question(String question, Subject subject, Difficulty difficulty) {
        this.question = question;
        this.subject = subject;
        this.difficulty = difficulty;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Question.Difficulty difficulty;

    @NotNull
    @NotEmpty
    private String question = "";

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<Answer> answers = new LinkedList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Subject subject;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return question;
    }
}
