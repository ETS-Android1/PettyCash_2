package com.example.pettycash;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SelectFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private  int textViewID =0;

    // TODO: Rename and change types of parameters
    private String title;
    public int titleString;
    private View view;
    private int icon;
    private String sherdPrefId,existValue;
    AddLine addLine ;
    int addLineNum =-1;


    FragmentListAdapter fragmentListAdapter;
    public SelectFragment(int select_lagal , int imageBtn , String sherdPrefId ,  String existValue) {
        titleString =select_lagal;
         icon = imageBtn;
         this.sherdPrefId = sherdPrefId;
        this.existValue = existValue;
        textViewID=-1;
        Log.v("F:"," title: "+select_lagal+" textId: "+String.valueOf(textViewID));

        // Required empty public constructor
    }
    public SelectFragment(int select_lagal , int imageBtn , int textViewID, String existValue , AddLine activity,int addLineNum) {
        titleString =select_lagal;
        icon = imageBtn;
        this.textViewID = textViewID;
        this.existValue = existValue;
        addLine = activity;
        this.addLineNum = addLineNum;
        Log.v("select pos",String.valueOf(addLineNum));

        // Required empty public constructor
    }
//    public SelectFragment(int select_lagal , int imageBtn ) {
//        titleString =select_lagal;
//        icon = imageBtn;
//        this.sherdPrefId = sherdPrefId;
//
//
//        // Required empty public constructor
//    }

    public SelectFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SelectFragment.
     */
    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_select, container, false);

        TextView titleView =view.findViewById(R.id.select_fragment_title);

        ImageButton cancelBtn = view.findViewById(R.id.select_fragment_cancel_btn);

        cancelBtn.setOnClickListener(this);


            RecyclerView recyclerView = view.findViewById(R.id.preferences_fragment_list);
            List<String> stringsList = new ArrayList<>();
            stringsList.add("kkk");
            stringsList.add("jjj");
            if (textViewID == -1) {
                Log.v("F:"," title: "+getActivity().getString(titleString)+" textId: "+String.valueOf(textViewID));

//                MainActivity.createToast(getActivity(),"sharedId: "+sherdPrefId);
                titleView.setText(getActivity().getString(titleString));

                fragmentListAdapter = new FragmentListAdapter(sherdPrefId,this.getContext(), stringsList, titleString, icon, (AppCompatActivity) getActivity(), existValue);
            }else if (textViewID>0) {
//                MainActivity.createToast(getActivity(),"tran");

                titleView.setText("Select " + addLine.getStringVal(titleString));
                fragmentListAdapter = new FragmentListAdapter(this.getContext(), stringsList, titleString, icon, addLine, textViewID,existValue,addLineNum);

            }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(fragmentListAdapter);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_fragment_cancel_btn:
                if (getActivity() instanceof UserPreferences) {
                    UserPreferences userPreferences = (UserPreferences) getActivity();
                    userPreferences.hideFragment();
                }else                 if (getActivity() instanceof AddLine) {


                        AddLine addLine = (AddLine) getActivity();
                        addLine.selectFragment.setVisibility(View.GONE);


                    }
                else {
                    AddTransaction addTransaction = (AddTransaction) getActivity();
                    addTransaction.hideFragment();
                }
                break;


        }

    }
}