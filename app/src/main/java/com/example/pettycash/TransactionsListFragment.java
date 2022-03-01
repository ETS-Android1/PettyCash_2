package com.example.pettycash;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionsListFragment extends Fragment {

    RecyclerView transListView;
    ImageView searchBtn;
    TransactionsListAdapter adapter;
    public TransactionsListFragment() {
        // Required empty public constructor
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


        // Inflate the layout for this fragment
        return view;
    }
}