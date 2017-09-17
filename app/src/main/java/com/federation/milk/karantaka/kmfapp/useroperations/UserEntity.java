package com.federation.milk.karantaka.kmfapp.useroperations;

import java.io.Serializable;

public class UserEntity implements Serializable {

    private final int amount;
    private final String firstName;
    private final String lastName;
    private final String personId;

    public UserEntity(int amount, String firstName, String lastName, String personId) {
        this.amount = amount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPersonId() {
        return personId;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", personId='" + personId + '\'' +
                '}';
    }
}
