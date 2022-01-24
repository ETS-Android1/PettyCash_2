package com.example.pettycash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragmanet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragmanet extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view ;
    AppCompatButton preferencesBtn,aboutBtn,logoutBtn;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;


    public SettingsFragmanet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragmanet.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragmanet newInstance(String param1, String param2) {
        SettingsFragmanet fragment = new SettingsFragmanet();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPref.edit();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings_fragmanet, container, false);

        preferencesBtn = view.findViewById(R.id.settings_preferences);
        aboutBtn = view.findViewById(R.id.settings_about);
        logoutBtn = view.findViewById(R.id.settings_logout);

        preferencesBtn.setOnClickListener(this);
        aboutBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.settings_preferences:
                Intent toPref = new Intent(getActivity(), UserPreferences.class);
                startActivity(toPref);
            break;

            case R.id.settings_logout:
                editor.clear().apply();
                Intent toLogin = new Intent(getActivity(), MainActivity.class);
                startActivity(toLogin);
        }

    }
}