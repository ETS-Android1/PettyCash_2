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

    @Query("SELECT * FROM TransactionModelView WHERE :transID == id")
    TransactionModelView getSingle(int transID);


    @Query("UPDATE  TransactionModelView SET total_amount = :totalAmount WHERE :transId == id")
    void updateTotalAmount(int transId, double totalAmount);

    @Query("SELECT * FROM TransactionModelView WHERE :status = status")
    List<TransactionModelView> getAllByStatus(String status);
}
