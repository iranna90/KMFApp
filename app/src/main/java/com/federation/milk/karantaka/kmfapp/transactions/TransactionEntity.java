package com.federation.milk.karantaka.kmfapp.transactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by iranna.patil on 10/09/2017.
 */

@JsonIgnoreProperties
public class TransactionEntity implements Serializable {

    private final long id;
    private final long amount;
    private final Date day;
    private final String personName;
    private final Type type;
    private final long closingBalance;
    private final int numberOfLiters;

    @JsonCreator
    public TransactionEntity(@JsonProperty("id") long id,
                             @JsonProperty("amount") long amount,
                             @JsonProperty("day") Date date,
                             @JsonProperty("numberOfLiters") int numberOfLiters,
                             @JsonProperty("personName") String personName,
                             @JsonProperty("transactionType") Type transactionType,
                             @JsonProperty("closingBalance") long closingBalance) {
        this.id = id;
        this.amount = amount;
        this.day = date;
        this.personName = personName;
        this.type = transactionType;
        this.closingBalance = closingBalance;
        this.numberOfLiters = numberOfLiters;
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

    public long getId() {
        return id;
    }

    public Date getDay() {
        return day;
    }

    public long getClosingBalance() {
        return closingBalance;
    }

    @Override
    public String toString() {
        return "TransactionEntity{" +
                "id=" + id +
                ", amount=" + amount +
                ", day=" + day +
                ", personName='" + personName + '\'' +
                ", type=" + type +
                ", closingBalance=" + closingBalance +
                '}';
    }
}
