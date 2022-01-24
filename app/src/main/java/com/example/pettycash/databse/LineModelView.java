package com.example.pettycash.databse;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
@Entity

public class LineModelView {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "transactionId")
    public String transactionId;
    @ColumnInfo(name = "category")
    public  String category;
    @ColumnInfo(name = "unit")
    public  String unit;
    @ColumnInfo(name = "item")
    public  String item;
    @ColumnInfo(name = "quantity")
    public String quantity;
    @ColumnInfo(name = "price")
    public  String price;
    @ColumnInfo(name = "amount")
    public  String amount;
    @ColumnInfo(name = "supplierName")
    public    String supplierName;
    @ColumnInfo(name = "invoiceNumber")
    public  String invoiceNumber;
    @ColumnInfo(name = "vatIvoiceNumber")
    public   String vatInvoiceNumber;
    @ColumnInfo(name = "billedToCustomer")
    public   String billedToCustomer;
    @ColumnInfo(name = "invoiceDate")
    public String invoiceDate;




    public LineModelView() {


    }


}
