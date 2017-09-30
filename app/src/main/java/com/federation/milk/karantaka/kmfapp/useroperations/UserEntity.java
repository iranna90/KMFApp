package com.federation.milk.karantaka.kmfapp.useroperations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UserEntity implements Serializable {

    private int amount;
    private final String firstName;
    private final String lastName;
    private final String personId;

    @JsonCreator
    public UserEntity(@JsonProperty("amount") int amount,
                      @JsonProperty("firstName") String firstName,
                      @JsonProperty("lastName") String lastName,
                      @JsonProperty("personId") String personId) {
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

    public UserEntity clone(final long newAmount) {
        return new UserEntity((int) newAmount, firstName, lastName, personId);
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "amount=" + amount +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", personId='" + personId + '\'' +
                '}';
    }
}
