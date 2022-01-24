package com.example.pettycash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserPreferences extends AppCompatActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener , Preference.OnPreferenceChangeListener {

    static String legal_ID = "3";
    static String business_ID = "4";
    static String project_ID = "5";
    static String department_ID = "6";


    TextView legalText, businessText, projectText, depatmentText;
    ImageButton cancelBtn,confirmBtn;
    FragmentManager fragmentManager;
    private static View fragment;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    SelectFragment legalSelectFragment, businessSelectFragment, projectSelectFragment, departmentSelectFragment;

    RelativeLayout legalLayout, businessLayout, projectLayout, departmentLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preferences);

        fragment = findViewById(R.id.preferences_fragment);

        fragment.setVisibility(View.GONE);

        cancelBtn = findViewById(R.id.preferences_cancel_btn);
        confirmBtn = findViewById(R.id.preferences_confirm_btn);

        confirmBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        fragmentManager = getSupportFragmentManager();


        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
       editor = sharedPref.edit();




        prepareLegal();
        prepareBusiness();
        prepareDepartment();
        prepareProject();


    }

    private void prepareLegal() {
        legalText = findViewById(R.id.preferences_legal_select_title);

        legalSelectFragment = new SelectFragment(R.string.select_lagal, R.drawable.outline_dashboard_customize_black_24,legal_ID,legalText.getText().toString());

        legalLayout = findViewById(R.id.preferences_legal_layout);

        legalLayout.setOnClickListener(this);

        String legalVal = sharedPref.getString(legal_ID, "MES");
        if (!legalVal.equals(""))
            legalText.setText(legalVal);
        editor.putString(legal_ID,legalVal).apply();


        fragmentManager.beginTransaction()
                .add(R.id.preferences_fragment, legalSelectFragment, "legal")
                .commit();

    }


    private void prepareBusiness() {
        businessText = findViewById(R.id.preferences_business_select_title);

        businessSelectFragment = new SelectFragment(R.string.select_business, R.drawable.outline_dashboard_customize_black_24,business_ID,businessText.getText().toString());

        businessLayout = findViewById(R.id.preferences_business_layout);

        businessLayout.setOnClickListener(this);

        String businessVal = sharedPref.getString(business_ID, "MES-BU");
        if (!businessVal.equals(""))
            businessText.setText(businessVal);
        editor.putString(legal_ID,businessVal).apply();



    }

    private void prepareProject() {
        projectText = findViewById(R.id.preferences_project_select_title);

        projectSelectFragment = new SelectFragment(R.string.select_project, R.drawable.outline_dns_black_24, project_ID,projectText.getText().toString());

        projectLayout = findViewById(R.id.preferences_project_layout);

        projectLayout.setOnClickListener(this);

        String projectVal = sharedPref.getString(project_ID, "M111");
        if (!projectVal.equals(""))
            projectText.setText(projectVal);
        editor.putString(legal_ID,projectVal).apply();



    }

    private void prepareDepartment() {
        depatmentText = findViewById(R.id.preferences_department_select_title);

        departmentSelectFragment = new SelectFragment(R.string.select_department, R.drawable.outline_account_tree_black_24, department_ID,depatmentText.getText().toString());

        departmentLayout = findViewById(R.id.preferences_department_layout);

        departmentLayout.setOnClickListener(this);

        String departmentVal = sharedPref.getString(department_ID, "BU1");
        if (!departmentVal.equals(""))
            depatmentText.setText(departmentVal);
        editor.putString(legal_ID,departmentVal).apply();



    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.preferences_legal_layout:
                Log.v("f", String.valueOf(fragmentManager.getFragments().indexOf((Object) legalSelectFragment)));
                legalSelectFragment = new SelectFragment(R.string.select_lagal, R.drawable.outline_dashboard_customize_black_24,legal_ID,legalText.getText().toString());

                if (fragmentManager.getFragments().indexOf((Object) legalSelectFragment) <= -1) {
                    Log.v("f", "e");
                    fragmentManager.beginTransaction()
                            .replace(R.id.preferences_fragment, legalSelectFragment, "legal")
                            .commit();

                    fragment.setVisibility(View.VISIBLE);
                } else {
                    Log.v("f", "no");

                    fragmentManager.beginTransaction()
                            .replace(R.id.preferences_fragment, legalSelectFragment, "legal")
                            .commit();
                    fragment.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.preferences_business_layout:
//                Log.v("f",String.valueOf(fragmentManager.getFragments().indexOf((Object) legalSelectFragment)) );
//
//                if (fragmentManager.getFragments().indexOf((Object) businessSelectFragment) <=-1) {
//                    Log.v("f","e");
//
//                    fragmentManager.beginTransaction()
//                            .add(R.id.preferences_fragment, businessSelectFragment, "bui")
//                            .commit();
//                    fragment.setVisibility(View.VISIBLE);
//                }else {
                Log.v("f", "no");
                businessSelectFragment = new SelectFragment(R.string.select_business, R.drawable.outline_dashboard_customize_black_24,business_ID,businessText.getText().toString());

                fragmentManager.beginTransaction()
                        .replace(R.id.preferences_fragment, businessSelectFragment, "bui")
                        .commit();
                fragment.setVisibility(View.VISIBLE);
//                }
                break;

            case R.id.preferences_project_layout:
                Log.v("f", String.valueOf(fragmentManager.getFragments().indexOf((Object) legalSelectFragment)));

//                if (fragmentManager.getFragments().indexOf((Object) projectSelectFragment) <=-1) {
//                    Log.v("f","e");
//
//                    fragmentManager.beginTransaction()
//                            .add(R.id.preferences_fragment, projectSelectFragment, "pro")
//                            .commit();
//                    fragment.setVisibility(View.VISIBLE);
//                }else {
//                    Log.v("f","no");
                projectSelectFragment = new SelectFragment(R.string.select_project, R.drawable.outline_dns_black_24, project_ID,projectText.getText().toString());

                fragmentManager.beginTransaction()
                        .replace(R.id.preferences_fragment, projectSelectFragment, "pro")
                        .commit();
                fragment.setVisibility(View.VISIBLE);
//                }
                break;

            case R.id.preferences_department_layout:
                Log.v("f", String.valueOf(fragmentManager.getFragments().indexOf((Object) legalSelectFragment)));
                departmentSelectFragment = new SelectFragment(R.string.select_department, R.drawable.outline_account_tree_24, department_ID,depatmentText.getText().toString());
//                if (fragmentManager.getFragments().indexOf((Object) departmentSelectFragment) <=-1) {
//                    Log.v("f","e");
//
//                    fragmentManager.beginTransaction()
//                            .add(R.id.preferences_fragment, departmentSelectFragment, "dep")
//                            .commit();
//                    fragment.setVisibility(View.VISIBLE);
//                }else {
//                    Log.v("f","no");

                fragmentManager.beginTransaction()
                        .replace(R.id.preferences_fragment, departmentSelectFragment, "dep")
                        .commit();
                fragment.setVisibility(View.VISIBLE);
//                }
                break;

            case R.id.preferences_cancel_btn:
                Intent toHome = new Intent(this, HomeContainer.class);
                startActivity(toHome);
                finish();
                break;

            case R.id.preferences_confirm_btn:
                String legalVal = sharedPref.getString(legal_ID, "MES");
                legalText.setText(legalVal);


                String busiVal = sharedPref.getString(business_ID, "MES-BU");
                businessText.setText(busiVal);


                String proVal = sharedPref.getString(project_ID, "M111");
                projectText.setText(proVal);

                String depVal = sharedPref.getString(department_ID, "BU1");
                depatmentText.setText(depVal);
                Intent toHome2 = new Intent(this, HomeContainer.class);
                startActivity(toHome2);
                finish();
                break;

        }
    }

    public static void hideFragment() {
        fragment.setVisibility(View.GONE);
//        sheredChange();
//        switch (view.getId()) {
//            case R.id.preferences_legal_layout:
//                break;
//
//            case R.id.preferences_business_layout:
////
//                break;
//
//            case R.id.preferences_project_layout:
//
//                break;
//
//            case R.id.preferences_department_layout:
//
//                break;


//        }


    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Toast.makeText(this, "changed", Toast.LENGTH_SHORT).show();

        String businessVal = sharedPref.getString(business_ID, "MES-BU");
        if (!businessVal.equals(""))
            businessText.setText(businessVal);

        String projectVal = sharedPref.getString(project_ID, "M111");
        if (!projectVal.equals(""))
            projectText.setText(projectVal);

        String departmentVal = sharedPref.getString(department_ID, "BU1");
        if (!departmentVal.equals(""))
            depatmentText.setText(departmentVal);


        String legalVal = sharedPref.getString(legal_ID, "MES");
        if (!legalVal.equals(""))
            legalText.setText(legalVal);


        return true;
    }

    public void shared() {

        Toast.makeText(this, "changed to ", Toast.LENGTH_SHORT).show();

            String legalVal = sharedPref.getString(legal_ID, "MES");
            legalText.setText(legalVal);


            String busiVal = sharedPref.getString(business_ID, "MES-BU");
            businessText.setText(busiVal);


            String proVal = sharedPref.getString(project_ID, "M111");
            projectText.setText(proVal);

            String depVal = sharedPref.getString(department_ID, "BU1");
            depatmentText.setText(depVal);

    }

    @Override
    protected void onResume() {
        super.onResume();
//                Toast.makeText(this, "Res", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Toast.makeText(this, "changed", Toast.LENGTH_SHORT).show();

        String businessVal = sharedPref.getString(business_ID, "MES-BU");
        if (!businessVal.equals(""))
            businessText.setText(businessVal);

        String projectVal = sharedPref.getString(project_ID, "M111");
        if (!projectVal.equals(""))
            projectText.setText(projectVal);

        String departmentVal = sharedPref.getString(department_ID, "BU1");
        if (!departmentVal.equals(""))
            depatmentText.setText(departmentVal);


        String legalVal = sharedPref.getString(legal_ID, "MES");
        if (!legalVal.equals(""))
            legalText.setText(legalVal);


    }
}