package com.ip2.myapp.backend.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;


@Entity
public class Subject extends AbstractEntity implements Cloneable {


    private String name;

    @OneToMany(mappedBy = "subject", fetch = FetchType.EAGER)
    private List<Question> questions = new LinkedList<>();


    public Subject() {

    }

    public Subject(String name) {
        setName(name);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    @Override
    public String toString() {
        return getName();
    }


}
