package com.example.pettycash.DAO;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pettycash.databse.LineModelViewDB;
import com.example.pettycash.databse.TransactionModelView;

import java.util.List;

@Dao
public interface LinesDAO {
    @Query("SELECT * FROM LineModelViewDB")
    List<LineModelViewDB> getAll();
   @Query("SELECT * FROM LineModelViewDB")
    LiveData<List<LineModelViewDB>> getAllLive();

    @Query("SELECT * FROM LineModelViewDB WHERE id IN (:id)")
    List<LineModelViewDB> loadAllByIds(int[] id);

//    @Query("SELECT * FROM LineModelView WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    LineModelView findByName(String first, String last);

    @Insert
    void insertAll(LineModelViewDB... users);

    @Insert
    void insertLine(LineModelViewDB user);


    @Delete
    void delete(LineModelViewDB user);


    @Query("DELETE FROM transactionmodelview")
    void delelteAll();

    @Query("SELECT * FROM LineModelViewDB WHERE :transID == transactionId")
    List<LineModelViewDB> ListofLines(int transID);


}
