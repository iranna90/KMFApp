package com.federation.milk.karantaka.kmfapp.transactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by iranna.patil on 10/09/2017.
 */

public class TransactionEntity {

    private final long amount;
    private final Date day;
    private final String personName;
    private final Type type;

    @JsonCreator
    public TransactionEntity(@JsonProperty("amount") long amount,
                             @JsonProperty("day") Date date,
                             @JsonProperty("numberOfLiters") int numberOfLiters,
                             @JsonProperty("personName") String personName) {
        this.amount = amount;
        this.day = date;
        this.personName = personName;
        this.type = numberOfLiters == 0 ? Type.PAID : Type.DEPOSITED;
    }

    public long getAmount() {
        return amount;
    }

    public Date getDate() {
        return day;
    }

    public String getPersonName() {
        return personName;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "TransactionEntity{" +
                "amount=" + amount +
                ", day=" + day +
                ", personName='" + personName + '\'' +
                ", type=" + type +
                '}';
    }
}
