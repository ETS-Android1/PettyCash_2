package com.example.pettycash.databse;

import android.net.Uri;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pettycash.LineModelView;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
@Entity

public class LineModelViewDB {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "transactionId")
    public int transactionId;
    @ColumnInfo(name = "category")
    public  String category;
    @ColumnInfo(name = "unit")
    public  String unit;
    @ColumnInfo(name = "item")
    public  String item;
    @ColumnInfo(name = "quantity")
    public int quantity;
    @ColumnInfo(name = "price")
    public  double price;
    @ColumnInfo(name = "amount")
    public  double amount;
    @ColumnInfo(name = "supplier_Name")
    public    String supplierName;
    @ColumnInfo(name = "invoice_Number")
    public  String invoiceNumber;
    @ColumnInfo(name = "vat_Invoice_Number")
    public   String vatInvoiceNumber;
    @ColumnInfo(name = "billedToCustomer")
    public   boolean billedToCustomer;
    @ColumnInfo(name = "invoice_Date")
    public long invoiceDate;
 @ColumnInfo(name = "cbs_Code")
    public String cbsCode;
 @ColumnInfo(name = "expenditure_type")
    public String expenditureType;





    public LineModelViewDB(int transactionId, String category, String unit, String item, int quantity, double price, double amount, String supplierName, String invoiceNumber, String vatInvoiceNumber, boolean billedToCustomer, long invoiceDate, String cbsCode, String expenditureType) {
//        super(transactionId,category,unit,item,quantity,price,amount,supplierName,invoiceNumber,vatInvoiceNumber,billedToCustomer,invoiceDate,cbsCode,expenditureType);
        this.transactionId = transactionId;
        this.category = category;
        this.unit = unit;
        this.item = item;
        if (quantity<= 0){
            this.quantity =1;
        }else {
            this.quantity = quantity;
        }
        this.price = price;
        this.amount = amount;
        this.supplierName = supplierName;
        this.invoiceNumber = invoiceNumber;
        this.vatInvoiceNumber = vatInvoiceNumber;
        this.billedToCustomer = billedToCustomer;
        Log.v("date of line on DB",invoiceDate+"");

        this.invoiceDate = invoiceDate;
        this.cbsCode = cbsCode;
        this.expenditureType = expenditureType;
    }


}
