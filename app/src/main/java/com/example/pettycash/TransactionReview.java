package com.example.pettycash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pettycash.Utality.Utlity;
import com.example.pettycash.databse.LineModelViewDB;
import com.example.pettycash.databse.TransactionModelView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class TransactionReview extends AppCompatActivity {
    TextView legalValueText, businessValueText, projectValueText, depatmentValueText ,dateValueText,statusValueText,vatValueText,amountValueText;
    RecyclerView linesLayout;
    TransReviewAdapter adapter;
    int currentTransId = -1;
    private List<LineModelViewDB> linesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_review);
        TransactionModelView transactionModelView = null;
        legalValueText = findViewById(R.id.trans_review_legal_entity_value);
        businessValueText = findViewById(R.id.trans_review_business_unit_value);
        projectValueText = findViewById(R.id.trans_review_project_value);
        depatmentValueText = findViewById(R.id.trans_review_department_value);
        dateValueText = findViewById(R.id.trans_review_date_value);
        statusValueText = findViewById(R.id.trans_review_status_value);
        Log.v("transIdFromADDLine", String.valueOf(getIntent().getIntExtra(Utlity.transId,-1)));
        currentTransId = getIntent().getIntExtra(Utlity.transId,-1);

        new Utlity.TaskRunner().executeAsync(new Utlity.GetTransCallable(this.getApplication(),currentTransId), (data) ->{
            TransactionModelView transDB = data;
            Log.v("transReview","trans ID : "+transDB.id);
            legalValueText.setText(transDB.legalEntry);
            businessValueText.setText(transDB.businessUnit);
            projectValueText.setText(transDB.project);
            depatmentValueText.setText(transDB.department);
            String myFormat="yyyy-MM-dd";
            SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ENGLISH);
            dateValueText.setText(dateFormat.format(transDB.date));
            statusValueText.setText(transDB.status);

            String isvat ="";
            if (transDB.isVat == false){
                isvat = "No";
            }else {
                isvat = "Yes";
            }
            vatValueText = findViewById(R.id.trans_review_vat_value);
            vatValueText.setText(isvat);

            amountValueText = findViewById(R.id.trans_review_total_amount_value);

            amountValueText.setText(transDB.total_amount+"(SAR)");





        });
        linesLayout = findViewById(R.id.trans_review_add_lines_layout);
//        linesLayout.setNestedScrollingEnabled(false);

        LinearLayoutManager  customLiner = new LinearLayoutManager(this){
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
        };
        linesLayout.setLayoutManager(customLiner);
        linesList = new ArrayList<>();
        adapter = new TransReviewAdapter(this,linesList);
        linesLayout.setAdapter(adapter);

        new Utlity.TaskRunner().executeAsync(new Utlity.GetLineCallable(this.getApplication(),currentTransId), (data) ->{
            List<LineModelViewDB> lines = new ArrayList<>();
            LineModelViewDB newLine = null;
            int i = 0;
            while (i <data.size()){
                newLine = data.get(i);
                Log.v("cateFormDB"+i,newLine.category);
                lines.add(newLine);
                i++;
            }
//            adapter.lineModelViews.addAll(data);
            Log.v("lineListDBSize", String.valueOf(data.size()));
            adapter.notifyDataSetChanged();

//        while ()
//        new Utlity.TaskRunner().executeAsync(new Utlity.GetAttachCallable(this.getApplication()),(data) ->{
//
//        });
        } );


    }
}