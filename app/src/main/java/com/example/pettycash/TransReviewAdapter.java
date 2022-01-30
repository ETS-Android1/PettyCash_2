package com.example.pettycash;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pettycash.Utality.Utlity;
import com.example.pettycash.databse.AttachmentModelView;
import com.example.pettycash.databse.LineModelViewDB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TransReviewAdapter extends RecyclerView.Adapter<TransReviewAdapter.ViewHolder> {

    //    private final int selectorTitle;
    Context context;
    List<String> stringList;
    int icon;
    UserPreferences userPreferences;
    String sharedPrefId;

    ViewHolder viewHolder;

    AttachmentAdapter attachmentAdapter;


    AddLine activity;
    List<LineModelViewDB> lineModelViews = new ArrayList<>();

    private DatePickerDialog.OnDateSetListener date;
    private Calendar myCalendar;
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    String currentPhotoPath;
    private boolean isBilled;
    boolean isGranted;


    public TransReviewAdapter(Context context, List<LineModelViewDB> lines) {
        this.context = context;
        this.activity = activity;
        lineModelViews.addAll(lines);
        Log.v("adpList", String.valueOf(lineModelViews.size()));
        myCalendar = Calendar.getInstance();


//        this.stringList = stringList;
//        this.selectorTitle = selctorTitle;
//        this.icon = icon;
//        this.activeActivity = activeActivity;
//        this.sharedPrefId = sharedPrefId;




    }

    public void updateDocs() {
        viewHolder.attachmentAdapter.notifyDataSetChanged();
//        Log.v("docsL", String.valueOf(lineModelViews.get(addLine.attachPos).docsList.size()));
//        addLine.camere = false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(context).inflate(R.layout.trans_review_recycle, parent, false);

        return new ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        LineModelViewDB current = lineModelViews.get(position);
//        List<AttachmentModelView> docs = current.docsList;
//        holder.attachmentAdapter.linePos = position + 1;
//        holder.attachmentAdapter.docList.clear();
//        holder.attachmentAdapter.docList.addAll(docs);
//        holder.attachmentAdapter.notifyDataSetChanged();

        int pos = position + 1;
//        Log.v("current : " + current.position, "adapter: " + position);
//        Log.v("current", "pos: " + position + " cat : " + current.category + " item : " + current.item + " unit : " + current.unit + " price : " + current.price + " quantity : " + current.quantity + " pClicked: " + Boolean.toString(current.priceClicked) + " qClicked " + current.quantityClicked);

//
        holder.title.setText("Add Line "+pos);
        if (current.category != null)
            holder.categoryValueText.setText(current.category);

        if (current.unit != null)
            holder.unitValueText.setText(current.unit);

        if (current.item != null)
            holder.itemValueText.setText(current.item);

        if (current.cbsCode != null)
            holder.cbsCodeValueText.setText(current.cbsCode);

        if (current.expenditureType != null)
            holder.expenditureTypeValueText.setText(current.expenditureType);

//        if (current.price != 0 && current.priceClicked)
            holder.priceValueText.setText(String.valueOf(current.price)+" (SAR)");
//
//        if (current.quantity > 1 && current.quantityClicked)
            holder.quantityValueText.setText(String.valueOf(current.quantity));
//
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ENGLISH);
            Log.v("date of line after",current.invoiceDate+"");
        holder.dateValueText.setText(dateFormat.format(current.invoiceDate));
//        double amount = current.price * current.quantity;
        holder.amountValueText.setText(current.amount+" (SAR)");
//
//        holder.billed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                current.billedToCustomer = isChecked;
//            }
//        });
//
//
//        holder.title.setText(addLine.getResources().getString(R.string.add_line) + " " + String.valueOf(pos));
//
//        date = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int day) {
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, month);
//                myCalendar.set(Calendar.DAY_OF_MONTH, day);
//
//                String myFormat = "yyyy-MM-dd";
//                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ENGLISH);
//                if (myCalendar.getTime() == null) {
//
//                    current.invoiceDate = myCalendar.getTime().getTime();
//                } else {
//                    current.invoiceDate = myCalendar.getTimeInMillis();
//
//                }
//                notifyDataSetChanged();
//            }
//        };


    }

    @Override
    public int getItemCount() {
        Log.v("transReviceadptsize", String.valueOf(lineModelViews.size()));
        return lineModelViews.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RecyclerView attachs_recyclerView;
        int ItemView;
        ImageButton imageView;
        TextView categoryValueText, itemValueText, quantityValueText, unitValueText, priceValueText, amountValueText, dateValueText, cbsCodeValueText, expenditureTypeValueText, title , invoiceDateValueText,invoiceNumberValueText , supplierValueText , billedValueText,vatValueText;
        ImageView attachmentBtn;
        AttachmentAdapter attachmentAdapter;
        List<Uri> docs;
        private SharedPreferences sharedPref;

        public ViewHolder(@NonNull View itemView) {


            super(itemView);
//            sharedPref = PreferenceManager.getDefaultSharedPreferences(addLine);
            title = itemView.findViewById(R.id.trans_review_line_title);
            categoryValueText = itemView.findViewById(R.id.trans_review_line_category_value);
            itemValueText = itemView.findViewById(R.id.trans_review_line_item_value);
            quantityValueText = itemView.findViewById(R.id.trans_review_line_quantity_value);
            unitValueText = itemView.findViewById(R.id.trans_review_line_unit_value);
            priceValueText = itemView.findViewById(R.id.trans_review_line_price_value);
            amountValueText = itemView.findViewById(R.id.trans_review_line_amount_value);
            invoiceDateValueText = itemView.findViewById(R.id.trans_review_line_invoice_date_value);
            invoiceNumberValueText = itemView.findViewById(R.id.trans_review_line_invoice_number_value);
            supplierValueText = itemView.findViewById(R.id.trans_review_line_supplier_name_value);
            vatValueText = itemView.findViewById(R.id.trans_review_line_vat_number_value);
            billedValueText = itemView.findViewById(R.id.trans_review_line_billed_value);
            cbsCodeValueText = itemView.findViewById(R.id.trans_review_line_cbs_code_value);
            expenditureTypeValueText = itemView.findViewById(R.id.trans_review_line_expenditure_type_value);
            dateValueText = itemView.findViewById(R.id.trans_review_line_invoice_date_value);


//            categoryValueText = itemView.findViewById(R.id.trans_review_line_category_value);


        }

        @Override
        public void onClick(View v) {

        }
    }
}
