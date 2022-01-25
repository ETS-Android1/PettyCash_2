package com.example.pettycash;

import android.net.Uri;

import com.example.pettycash.databse.AttachmentModelView;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LineModelView {
    public double price = 0;
    public int quantity =1;
    public String invoiceNumber;
    public String supplierName;
    public long vatInvoiceNumber;
    public long invoiceDate;
    public boolean billedToCustomer;
    public List<AttachmentModelView> docsList;
    public double amount;
    int position;
    String category;
    String unit;
    String item;
    String cbsCode;
    String expenditureType;
    boolean priceClicked,quantityClicked;


    public LineModelView(int position) {
        this.position = position;
        invoiceDate = Calendar.getInstance().getTimeInMillis();
        docsList = new ArrayList<>();

    }

    public LineModelView(int position, String category, String unit, String item) {
        this.position = position;
        invoiceDate = Calendar.getInstance().getTimeInMillis();

        this.category = category;
        this.unit = unit;
        this.item = item;
        docsList = new ArrayList<>();

    }
}
