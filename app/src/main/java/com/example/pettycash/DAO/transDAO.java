    package com.example.pettycash.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pettycash.databse.TransactionModelView;

import java.util.List;

@Dao
public interface transDAO {

    @Insert
    void insertTransaction(TransactionModelView trans);

    @Query("SELECT * FROM TransactionModelView")
    LiveData<List<TransactionModelView>> getAll();

    @Query("SELECT * FROM TransactionModelView")
    List<TransactionModelView> getAllNotLive();


    @Query("DELETE FROM transactionmodelview")
    void delelteAll();
}
