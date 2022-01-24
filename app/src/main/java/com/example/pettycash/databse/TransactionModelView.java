package com.example.pettycash.databse;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TransactionModelView {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "legalEntry")

    public String legalEntry;
    @ColumnInfo(name = "businessUnit")
    public  String businessUnit;
    @ColumnInfo(name = "project")
    public   String project;
    @ColumnInfo(name = "department")
    public  String department;


    public TransactionModelView(String legalEntity, String businessUnit, String project, String department) {
        this.legalEntry = legalEntity;
        this.businessUnit = businessUnit;
        this.project = project;
        this.department = department;

    }

    public TransactionModelView() {
    }
}
