package com.example.pettycash.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pettycash.databse.LineModelView;

import java.util.List;

@Dao
public interface LinesDAO {
    @Query("SELECT * FROM LineModelView")
    List<LineModelView> getAll();

    @Query("SELECT * FROM LineModelView WHERE id IN (:id)")
    List<LineModelView> loadAllByIds(int[] id);

//    @Query("SELECT * FROM LineModelView WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    LineModelView findByName(String first, String last);

    @Insert
    void insertAll(LineModelView... users);

    @Insert
    void insertLine(LineModelView user);


    @Delete
    void delete(LineModelView user);
}
