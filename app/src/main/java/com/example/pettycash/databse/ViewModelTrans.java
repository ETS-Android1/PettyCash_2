package com.example.pettycash.databse;

import com.example.pettycash.DAO.transDAO;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

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
        
        mDB.databaseWriteExecutor.execute(()->{
            transDAO.insertTransaction(trans);
        });
    }
}
