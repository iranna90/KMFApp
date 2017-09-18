package com.federation.milk.karantaka.kmfapp.transactions;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

/**
 * Created by iranna.patil on 18/09/2017.
 */

public class UserTransactions {

    private final List<TransactionEntity> transactions;

    @JsonCreator
    public UserTransactions(List<TransactionEntity> transactions) {
        this.transactions = transactions;
    }

    public List<TransactionEntity> getTransactions() {
        return transactions;
    }
}
