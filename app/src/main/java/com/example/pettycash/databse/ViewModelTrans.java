package com.example.pettycash.databse;

import com.example.pettycash.DAO.transDAO;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewModelTrans extends AndroidViewModel {

    private LiveData<List<TransactionModelView>> allData ;
    private TransRepository repository;
    private AppDatabase mDB ;
    transDAO transDAO;


    public ViewModelTrans(@NonNull Application application) {
        super(application);
        repository = new TransRepository(application);
        allData = repository.getAllTrans();
        mDB = AppDatabase.getInstance(application);
        transDAO = mDB.transDao();

    }


    public void addTrans(TransactionModelView trans){
        Log.e("test","test");
        new Executor() {
            @Override
            public void execute(Runnable command) {
                mDB.transDao().insertTransaction(trans);

            }
        };
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mDB.transDao().insertTransaction(trans);

            }
        });

        new Thread(() -> mDB.transDao().insertTransaction(trans)).start();

    }
}
