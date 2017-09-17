package com.federation.milk.karantaka.kmfapp.useroperations;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Persons {

    private final UserEntity[] users;

    @JsonCreator
    public Persons(UserEntity[] users) {
        this.users = users;
    }

    public UserEntity[] getUsers() {
        return users;
    }
}
