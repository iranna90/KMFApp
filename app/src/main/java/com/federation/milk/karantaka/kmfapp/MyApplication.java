package com.federation.milk.karantaka.kmfapp;

import android.app.Application;

/**
 * Created by iranna.patil on 18/09/2017.
 */

public class MyApplication extends Application {

    public String getDairyId() {
        return dairyId;
    }

    public void setDairyId(String dairyId) {
        this.dairyId = dairyId;
    }

    private String dairyId = "khajuri";

    private int pricePerLiter = 41;

    public int getPricePerLiter() {
        return pricePerLiter;
    }

    public void setPricePerLiter(int pricePerLiter) {
        this.pricePerLiter = pricePerLiter;
    }
}
