package com.ip2.myapp.backend.entity;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Result extends AbstractEntity {

    public Result() {
    }

    public Result(double score, User user, Subject subject) {
        this.score = score;
        this.user = user;
        this.subject = subject;
    }

    @NotNull
    double score;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    Subject subject;


    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;


    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
