package com.example.pettycash;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pettycash.Utality.Utlity;
import com.example.pettycash.databse.TransactionModelView;

import java.util.List;


public class TransactionsListFragment extends Fragment {

    RecyclerView transListView;
    ImageView searchBtn;
    TransactionsListAdapter adapter;
    TextView titleView;
    Context mContext;
    String title="";
    List<TransactionModelView> transList;
    Application mApplication;
    private String newTitle;

    public TransactionsListFragment(Context context , Application application, String title) {
        mContext =context;
        this.title = title;
        mApplication = application;
    }
    public TransactionsListFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions_list, container, false);

        transListView = view.findViewById(R.id.trans_list_recycler);
        searchBtn = view.findViewById(R.id.trans_list_search_btn);
        titleView = view.findViewById(R.id.trnas_list_title);
        if (title .equals(Utlity.INCOMPLETE)){
            newTitle = getResources().getString(R.string.incomplete_transactions_review);
        }else   if (title .equals(Utlity.APPROVED)){
            newTitle = getResources().getString(R.string.approved_transactions_review);
        }
        else   if (title .equals(Utlity.UNDER_APPROVAL)){
            newTitle = getResources().getString(R.string.under_approval_transactions_review);
        }
        titleView.setText(newTitle);

        new Utlity.TaskRunner().executeAsync(new Utlity.GetTransListCallable(getActivity().getApplication(), title),(data)->{
            transList = data;
            Log.v("tSizeF",String.valueOf(transList.size()));

            TransactionsListAdapter adapter = new TransactionsListAdapter(getContext(),transList,title);

            LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
            transListView.setLayoutManager(linearLayout);
            transListView.setAdapter(adapter);
        });





        // Inflate the layout for this fragment
        return view;
    }
}