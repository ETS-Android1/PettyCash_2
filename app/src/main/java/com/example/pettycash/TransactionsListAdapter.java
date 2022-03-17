package com.example.pettycash;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pettycash.databse.TransactionModelView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TransactionsListAdapter extends RecyclerView.Adapter<TransactionsListAdapter.ViewHolder> {
    Context mContext ;
    String title ;
    List<TransactionModelView> transList = new ArrayList<>();

    public TransactionsListAdapter(Context mContext , List<TransactionModelView> transList) {
        this.mContext = mContext;
        this.transList.clear();
        this.transList.addAll(transList);
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
        if (title != null)
        holder.transTitle.setText(title);
        if (current.project != null)
        holder.transProject.setText(current.project);
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ENGLISH);
        holder.transDate.setText(String.valueOf( dateFormat.format(current.date)));
        if (current.businessUnit != null)
        holder.transBusinessUnit.setText(current.businessUnit);
        holder.transAmount.setText(String.valueOf(current.total_amount));

    }

    @Override
    public int getItemCount() {

        Log.v("transListSize", String.valueOf(transList.size()));
        return transList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView transTitle,transProject,transDate,transBusinessUnit,transStatus,transAmount;
        AppCompatButton firstBtn,seconBtn,thirdBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            transTitle = itemView.findViewById(R.id.trans_list_Item_title);
            transProject = itemView.findViewById(R.id.trans_list_Item_project);
            transDate = itemView.findViewById(R.id.trans_list_Item_date);
            transBusinessUnit = itemView.findViewById(R.id.trans_list_Item_bu_value);
            transAmount = itemView.findViewById(R.id.trans_list_Item_amount_value);
            firstBtn = itemView.findViewById(R.id.trans_list_Item_first_btn);
            firstBtn = itemView.findViewById(R.id.trans_list_Item_first_btn);
            thirdBtn= itemView.findViewById(R.id.trans_list_Item_third_btn);
        }
    }
}
