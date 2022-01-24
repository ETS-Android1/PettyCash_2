package com.example.pettycash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteTransactionListener;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pettycash.DAO.transDAO;
import com.example.pettycash.Utality.Utlity;
import com.example.pettycash.databse.AppDatabase;
import com.example.pettycash.databse.TransactionModelView;
import com.example.pettycash.databse.ViewModelTrans;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.pettycash.UserPreferences.business_ID;
import static com.example.pettycash.UserPreferences.department_ID;
import static com.example.pettycash.UserPreferences.legal_ID;
import static com.example.pettycash.UserPreferences.project_ID;
import static com.example.pettycash.databse.AppDatabase.databaseWriteExecutor;
import static com.example.pettycash.databse.AppDatabase.instance;
import static java.time.chrono.IsoChronology.INSTANCE;

public class AddTransaction extends AppCompatActivity implements View.OnClickListener {
    TextView legalText, businessText, projectText, depatmentText ,dateText;
    ImageButton cancelBtn,confirmBtn;
    FragmentManager fragmentManager;
    private static View fragment;
    SharedPreferences sharedPref;

    SelectFragment legalSelectFragment, businessSelectFragment, projectSelectFragment, departmentSelectFragment;

    RelativeLayout legalLayout, businessLayout, projectLayout, departmentLayout;

    RelativeLayout dateLayout;
    DatePicker datePicker;
    private Calendar myCalendar = Calendar.getInstance();

    SettingsFragmanet settingsFragmanet;
    DatePickerDialog.OnDateSetListener date;
    private View keyboard;

    public AppDatabase db ;
    ViewModelTrans transViewModelProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);






//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                db.transDao().insertTransaction(new TransactionModelView());
//            }
//
//        }).start();

        transViewModelProvider = new ViewModelProvider(this).get(ViewModelTrans.class);


        fragment = findViewById(R.id.add_transaction_fragment);

        fragment.setVisibility(View.GONE);

//        fragmentTitle = findViewById(R.id.select_fragment_title);
        cancelBtn = findViewById(R.id.new_transaction_cancel_btn);



        dateLayout = findViewById(R.id.new_transaction_date_layout);


//        datePicker = findViewById(R.id.new_transaction_date);
            dateText = findViewById(R.id.new_transaction_date_text);
//        textDate.setOnClickListener(this);
        updateLabel();

        fragment = findViewById(R.id.add_transaction_fragment);

        fragment.setVisibility(View.GONE);

        confirmBtn = findViewById(R.id.new_transaction_confirm_btn);

        confirmBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        fragmentManager = getSupportFragmentManager();



        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);


        prepareLegal();
        prepareBusiness();
        prepareDepartment();
        prepareProject();






        date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        dateLayout.setOnClickListener(this);
    }

    private void prepareLegal() {
        legalText = findViewById(R.id.new_transaction_legal_select_title);

        legalSelectFragment = new SelectFragment(R.string.select_lagal, 0,legal_ID,legalText.getText().toString());

        legalLayout = findViewById(R.id.new_transaction_legal_layout);

        legalLayout.setOnClickListener(this);

        String legalVal = sharedPref.getString(legal_ID, "MES");
        if (!legalVal.equals(""))
            legalText.setText(legalVal);

        fragmentManager.beginTransaction()
                .add(R.id.add_transaction_fragment, legalSelectFragment, "legal")
                .commit();

    }


    private void prepareBusiness() {
        businessText = findViewById(R.id.new_transaction_business_select_title);

        businessSelectFragment = new SelectFragment(R.string.select_business, R.drawable.outline_dashboard_customize_black_24,business_ID, businessText.getText().toString());

        businessLayout = findViewById(R.id.new_transaction_business_layout);

        businessLayout.setOnClickListener(this);

        String businessVal = sharedPref.getString(business_ID, "MES-BU");
        if (!businessVal.equals(""))
            businessText.setText(businessVal);


    }

    private void prepareProject() {
        projectText = findViewById(R.id.new_transaction_project_select_title);


        projectSelectFragment = new SelectFragment(R.string.select_project, R.drawable.outline_dns_black_24, project_ID, projectText.getText().toString());

        projectLayout = findViewById(R.id.new_transaction_project_layout);

        projectLayout.setOnClickListener(this);

        String projectVal = sharedPref.getString(project_ID, "M111");
        if (!projectVal.equals(""))
            projectText.setText(projectVal);


    }

    private void prepareDepartment() {
        depatmentText = findViewById(R.id.new_transaction_department_select_title);


        departmentLayout = findViewById(R.id.new_transaction_department_layout);

        departmentLayout.setOnClickListener(this);

        String departmentVal = sharedPref.getString(department_ID, "BU1");
        if (!departmentVal.equals(""))
            depatmentText.setText(departmentVal);
        departmentSelectFragment = new SelectFragment(R.string.select_department, R.drawable.outline_account_tree_24, department_ID, depatmentText.getText().toString());


    }

    private void updateLabel(){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ENGLISH);
        if (myCalendar.getTime() == null) {

            dateText.setText(dateFormat.format(myCalendar.getTime()));
        }else {
            dateText.setText(dateFormat.format(myCalendar.getTimeInMillis()));

        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.new_transaction_legal_layout:
                keyboard = getCurrentFocus();


                if (keyboard == null) {
                    keyboard = new View(this);
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(keyboard.getWindowToken(), 0);

                Log.v("f", String.valueOf(fragmentManager.getFragments().indexOf((Object) legalSelectFragment)));
                legalSelectFragment = new SelectFragment(R.string.select_lagal, 0,legal_ID,legalText.getText().toString());

                if (fragmentManager.getFragments().indexOf((Object) legalSelectFragment) <= -1) {
                    Log.v("f", "e");
                    fragmentManager.beginTransaction()
                            .replace(R.id.add_transaction_fragment, legalSelectFragment, "legal")
                            .commit();

                    fragment.setVisibility(View.VISIBLE);
                } else {
                    Log.v("f", "no");

                    fragmentManager.beginTransaction()
                            .replace(R.id.add_transaction_fragment, legalSelectFragment, "legal")
                            .commit();
                    fragment.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.new_transaction_business_layout:
//                Log.v("f",String.valueOf(fragmentManager.getFragments().indexOf((Object) legalSelectFragment)) );
//
//                if (fragmentManager.getFragments().indexOf((Object) businessSelectFragment) <=-1) {
//                    Log.v("f","e");
//
//                    fragmentManager.beginTransaction()
//                            .add(R.id.new_transaction_fragment, businessSelectFragment, "bui")
//                            .commit();
//                    fragment.setVisibility(View.VISIBLE);
//                }else {
                Log.v("f", "no");
                businessSelectFragment = new SelectFragment(R.string.select_business, R.drawable.outline_dashboard_customize_black_24,business_ID, businessText.getText().toString());

                fragmentManager.beginTransaction()
                        .replace(R.id.add_transaction_fragment, businessSelectFragment, "bui")
                        .commit();
                fragment.setVisibility(View.VISIBLE);
//                }
                break;

            case R.id.new_transaction_project_layout:
                Log.v("f", String.valueOf(fragmentManager.getFragments().indexOf((Object) legalSelectFragment)));
                projectSelectFragment = new SelectFragment(R.string.select_project, R.drawable.outline_dns_black_24, project_ID, projectText.getText().toString());

//                if (fragmentManager.getFragments().indexOf((Object) projectSelectFragment) <=-1) {
//                    Log.v("f","e");
//
//                    fragmentManager.beginTransaction()
//                            .add(R.id.new_transaction_fragment, projectSelectFragment, "pro")
//                            .commit();
//                    fragment.setVisibility(View.VISIBLE);
//                }else {
//                    Log.v("f","no");

                fragmentManager.beginTransaction()
                        .replace(R.id.add_transaction_fragment, projectSelectFragment, "pro")
                        .commit();
                fragment.setVisibility(View.VISIBLE);
//                }
                break;

            case R.id.new_transaction_department_layout:
                Log.v("f", String.valueOf(fragmentManager.getFragments().indexOf((Object) legalSelectFragment)));

//                if (fragmentManager.getFragments().indexOf((Object) departmentSelectFragment) <=-1) {
//                    Log.v("f","e");
//
//                    fragmentManager.beginTransaction()
//                            .add(R.id.new_transaction_fragment, departmentSelectFragment, "dep")
//                            .commit();
//                    fragment.setVisibility(View.VISIBLE);
//                }else {
//                    Log.v("f","no");
                departmentSelectFragment = new SelectFragment(R.string.select_department, R.drawable.outline_account_tree_24, department_ID, depatmentText.getText().toString());

                fragmentManager.beginTransaction()
                        .replace(R.id.add_transaction_fragment, departmentSelectFragment, "dep")
                        .commit();
                fragment.setVisibility(View.VISIBLE);
//                }
                break;

            case R.id.new_transaction_cancel_btn:
                Intent toHome = new Intent(this, HomeContainer.class);
                startActivity(toHome);
                finish();
                break;

            case R.id.new_transaction_confirm_btn:
                insertTransToDB();
                Intent toHome2 = new Intent(this, AddLine.class);
                startActivity(toHome2);

                break;

            case R.id.new_transaction_date_layout:
                new DatePickerDialog(AddTransaction.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

        }
    }

    private void insertTransToDB() {

                    String legalEntity,businessUnit,project,department;

                    legalEntity = legalText.getText().toString();
                    businessUnit = businessText.getText().toString();
                    project = projectText.getText().toString();
                    department = depatmentText.getText().toString();

                    TransactionModelView newTrans = new TransactionModelView(legalEntity,businessUnit,project,department);
                    transViewModelProvider.addTrans(newTrans);




    }


    public static void hideFragment() {
        fragment.setVisibility(View.GONE);
//

    }

    public void shared(String s, int id) {

        switch (id) {
            case R.string.select_lagal:


            legalText.setText(s);
            break;

            case R.string.select_business:
            businessText.setText(s);
            break;

            case R.string.select_project:

            projectText.setText(s);
    break;

            case R.string.select_department:

            depatmentText.setText(s);
            break;
        }
    }

}