package com.example.pettycash.databse;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.pettycash.DAO.transDAO;

import java.util.List;

public class TransRepository {
    private transDAO mtransDao ;


    private LiveData<List<TransactionModelView>> transList;


    public TransRepository(Application application) {

        AppDatabase db = AppDatabase.getInstance(application);
        this.mtransDao = db.transDao();

        transList = mtransDao.getAll();




    }


    public void addTrans ( TransactionModelView transactionModelView){
        mtransDao.insertTransaction(transactionModelView);
    }
    public LiveData<List<TransactionModelView>> getAllTrans(){
        return transList;
    }
}
