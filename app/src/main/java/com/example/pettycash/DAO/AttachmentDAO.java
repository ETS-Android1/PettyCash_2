package com.example.pettycash.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pettycash.databse.AttachmentModelView;
import com.example.pettycash.databse.LineModelViewDB;
import com.example.pettycash.databse.TransactionModelView;

import java.util.Collection;
import java.util.List;

@Dao
public interface AttachmentDAO {
    @Query("SELECT * FROM AttachmentModelView")
    List<AttachmentModelView> getAll();

    @Insert
    void insertAttachment(AttachmentModelView trans);


    @Query("DELETE FROM transactionmodelview")
    void delelteAll();

    @Query("SELECT * FROM AttachmentModelView WHERE :transID == transId")
    List<AttachmentModelView> getAllbyTransId(int transID);

}
