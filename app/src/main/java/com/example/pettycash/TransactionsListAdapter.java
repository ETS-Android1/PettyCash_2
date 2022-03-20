package com.example.pettycash;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pettycash.Utality.Utlity;
import com.example.pettycash.databse.TransactionModelView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TransactionsListAdapter extends RecyclerView.Adapter<TransactionsListAdapter.ViewHolder> {
    Context mContext ;
    String title ;
    List<TransactionModelView> transList = new ArrayList<>();

    public TransactionsListAdapter(Context mContext , List<TransactionModelView> transList , String title) {
        this.mContext = mContext;
        this.transList.clear();
        this.transList.addAll(transList);
        this.title = title;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(mContext).inflate(R.layout.trans_list_recycler_view_layout,parent,false);


        return new ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionModelView current = transList.get(position);
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ENGLISH);
        String date = String.valueOf( dateFormat.format(current.date));
        if (title != null)
        holder.transTitle.setText(current.legalEntry +" - "+current.businessUnit+" - "+date);

        if (current.project != null)
        holder.transProject.setText(current.project);

        holder.transDate.setText(date);
        if (current.businessUnit != null)
        holder.transBusinessUnit.setText(current.businessUnit);
        if (current.status != null)
        holder.transStatus.setText(current.status);
        holder.transAmount.setText(String.valueOf(current.total_amount));

    }

    @Override
    public int getItemCount() {

        Log.v("transListSize", String.valueOf(transList.size()));
        return transList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         RelativeLayout transStatusLayout;
        TextView transTitle,transProject,transDate,transBusinessUnit,transStatus,transAmount;
        AppCompatButton firstBtn,seconBtn,thirdBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            transTitle = itemView.findViewById(R.id.trans_list_Item_title);
            transProject = itemView.findViewById(R.id.trans_list_Item_project);
            transDate = itemView.findViewById(R.id.trans_list_Item_date);
            transBusinessUnit = itemView.findViewById(R.id.trans_list_Item_bu_value);
            transAmount = itemView.findViewById(R.id.trans_list_Item_amount_value);
            transStatus = itemView.findViewById(R.id.trans_list_Item_status_value);
            transStatusLayout = itemView.findViewById(R.id.trans_list_Item_status_layout);
            firstBtn = itemView.findViewById(R.id.trans_list_Item_first_btn);
            seconBtn = itemView.findViewById(R.id.trans_list_Item_second_btn);
            thirdBtn= itemView.findViewById(R.id.trans_list_Item_third_btn);
            firstBtn.setOnClickListener(this);
            seconBtn.setOnClickListener(this);
            thirdBtn.setOnClickListener(this);

            if (title != null && title.equals(Utlity.UNDER_APPROVAL)){
                seconBtn.setVisibility(View.INVISIBLE);
            firstBtn.setText(mContext.getResources().getString(R.string.view));
           thirdBtn.setText(mContext.getResources().getString(R.string.withdraw));
           transStatusLayout.setBackground(mContext.getResources().getDrawable(R.drawable.trans_status_under_approval));
            }else if (title != null && title.equals(Utlity.INCOMPLETE)){
                seconBtn.setText(mContext.getResources().getString(R.string.edit));
                firstBtn.setText(mContext.getResources().getString(R.string.view));
                thirdBtn.setText(mContext.getResources().getString(R.string.withdraw));
                transStatusLayout.setBackground(mContext.getResources().getDrawable(R.drawable.trans_status_incomplete));
            }else if (title != null && title.equals(Utlity.APPROVED)){
                firstBtn.setVisibility(View.INVISIBLE);
                seconBtn.setText(mContext.getResources().getString(R.string.view));
                thirdBtn.setVisibility(View.INVISIBLE);
                transStatusLayout.setBackground(mContext.getResources().getDrawable(R.drawable.trans_status_approved));
            }
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.trans_list_Item_first_btn:

                    Intent toEditTransaction = new Intent(mContext,AddTransaction.class);
                    toEditTransaction.putExtra(Utlity.transId, transList.get(getAdapterPosition()).id);
                    mContext.startActivity(toEditTransaction);
            }

        }
    }
}
