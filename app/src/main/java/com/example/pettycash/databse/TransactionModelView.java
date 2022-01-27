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
     @ColumnInfo(name = "isVat")
    public  boolean isVat;
     @ColumnInfo(name = "date")
    public  long date;
      @ColumnInfo(name = "description")
    public  String description;
    @ColumnInfo(name = "status")
    public  String status;


    @ColumnInfo(name = "total_amount")
    public double total_amount;





    public TransactionModelView(String legalEntity, String businessUnit, String project, String department,boolean isVat , long date , String description,String status) {
        this.legalEntry = legalEntity;
        this.businessUnit = businessUnit;
        this.project = project;
        this.department = department;
        this.isVat = isVat;
        this.date = date;
        this.description = description;
        this.status  = "incomplete";
    }

    public TransactionModelView() {
    }


    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }
}
