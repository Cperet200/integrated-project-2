package com.ip2.myapp.backend.entity;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Result {

    public Result() {
    }

    public Result(int score, User user) {
        this.score = score;
        this.user = user;
    }

    @NotNull
    @NotEmpty
    int score;


    @NotNull
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    User user;
}
