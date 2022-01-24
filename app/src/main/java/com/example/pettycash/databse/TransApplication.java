package com.example.pettycash.databse;

import android.app.Application;

public class TransApplication extends Application {
    AppDatabase db= AppDatabase.getInstance(this);

}
