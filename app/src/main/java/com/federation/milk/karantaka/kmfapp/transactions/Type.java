package com.federation.milk.karantaka.kmfapp.transactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by iranna.patil on 10/09/2017.
 */

public enum Type {
    PAID("PAID"),
    DEPOSITED("DEPOSIT");

    private final String transactionType;

    Type(String transactionType) {
        this.transactionType = transactionType;
    }

    @JsonCreator
    public Type findTransactionType(String label) {
        for (Type t : Type.values()) {
            if (t.transactionType.equals(label)) {
                return t;
            }
        }
        throw new RuntimeException("Invalid transaction type");
    }

    @JsonValue
    public String getTransactionType() {
        return transactionType;
    }
}
