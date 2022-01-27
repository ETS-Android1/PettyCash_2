package com.example.pettycash.databse;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pettycash.DAO.AttachmentDAO;
import com.example.pettycash.DAO.LinesDAO;
import com.example.pettycash.DAO.transDAO;
import com.example.pettycash.Utality.Utlity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {LineModelViewDB.class,TransactionModelView.class,AttachmentModelView.class}, version = 6 , exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static Object LOCK = new Object();

    public abstract LinesDAO linesDao();
    public abstract transDAO transDao();
    public abstract AttachmentDAO attachmentDao();

    private static int NUMBER_OF_THREADS =4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static volatile  AppDatabase instance;

    public static AppDatabase getInstance(Context mContext){
        AppDatabase temp = instance;

        if (temp != null){
            return temp;
        }else {
            synchronized (LOCK) {
                AppDatabase appDatabase = Room.databaseBuilder(
                        mContext,
                        AppDatabase.class,
                        Utlity.databaseName).fallbackToDestructiveMigration()

                        .build();

                instance = appDatabase;
                return instance;

            }


            }
        }
    }



