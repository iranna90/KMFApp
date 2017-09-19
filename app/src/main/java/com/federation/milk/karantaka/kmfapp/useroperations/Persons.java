package com.federation.milk.karantaka.kmfapp.useroperations;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class Persons {

    private final List<UserEntity> users;

    @JsonCreator
    public Persons(List<UserEntity> users) {
        this.users = users;
    }

    public List<UserEntity> getUsers() {
        return users;
    }
}
