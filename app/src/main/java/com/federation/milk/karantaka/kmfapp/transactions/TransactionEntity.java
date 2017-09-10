package com.federation.milk.karantaka.kmfapp.transactions;

import java.util.Date;

/**
 * Created by iranna.patil on 10/09/2017.
 */

public class TransactionEntity {

    private final long amount;
    private final Date date;
    private final int numberOfLiters;
    private final String personName;
    private final Type type;

    public TransactionEntity(long amount, Date date, int numberOfLiters, String personName, Type type) {
        this.amount = amount;
        this.date = date;
        this.numberOfLiters = numberOfLiters;
        this.personName = personName;
        this.type = type;
    }

    public long getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public int getNumberOfLiters() {
        return numberOfLiters;
    }

    public String getPersonName() {
        return personName;
    }

    public Type getType() {
        return type;
    }
}
