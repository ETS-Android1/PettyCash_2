package com.example.pettycash.databse;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.pettycash.DAO.LinesDAO;
import com.example.pettycash.DAO.transDAO;

import java.util.List;

public class LinesRepository {
     public LinesDAO mLinesDAO ;


    private LiveData<List<LineModelViewDB>> list;
    AppDatabase db;

    public LinesRepository(Application application) {

         db = AppDatabase.getInstance(application);
        this.mLinesDAO = db.linesDao();





    }


    public void addLine ( LineModelViewDB LineModelViewDB){
        mLinesDAO.insertLine(LineModelViewDB);
    }
    public LiveData<List<LineModelViewDB>> getAllTrans(){
        return mLinesDAO.getAllLive();
    }
}
