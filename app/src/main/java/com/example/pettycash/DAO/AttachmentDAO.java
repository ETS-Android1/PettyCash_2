package com.example.pettycash.DAO;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.pettycash.databse.AttachmentModelView;
import com.example.pettycash.databse.TransactionModelView;

@Dao
public interface AttachmentDAO {

    @Insert
    void insertTransaction(AttachmentModelView trans);

}
