package com.example.pettycash.Utality;
import android.util.Log;

import java.util.concurrent.Callable;

public class Action implements Callable<Long> {
    private long number;

    @Override
    public Long call() throws Exception {
        return number * 2;
    }

    public Action() {
        super();
    }

    public Action(long number) {
        super();
        this.number = number;
        Log.v("appBack", String.valueOf(number));

    }
}
