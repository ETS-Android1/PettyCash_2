package com.example.pettycash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.pettycash.Utality.Utlity;
import com.example.pettycash.databse.AppDatabase;
import com.example.pettycash.databse.TransactionModelView;
import com.example.pettycash.databse.ViewModelTrans;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.Callable;

import static com.example.pettycash.UserPreferences.business_ID;
import static com.example.pettycash.UserPreferences.department_ID;
import static com.example.pettycash.UserPreferences.legal_ID;
import static com.example.pettycash.UserPreferences.project_ID;

public class AddTransaction extends AppCompatActivity implements View.OnClickListener, Callable, CompoundButton.OnCheckedChangeListener
        , ActivityCompat.OnRequestPermissionsResultCallback {
    TextView legalText, businessText, projectText, depatmentText ,dateText;
    ImageView confirmBtn;
    AppCompatImageView cancelBtn;
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
    private Switch vat;
    private long dateTime;

    public AppDatabase db ;
    ViewModelTrans transViewModelProvider;
    private boolean isVatChecked;
    private Integer transID;
    private int currentTransID;
    private TransactionModelView currentTrans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        currentTransID = getIntent().getIntExtra(Utlity.transId, -1);


        db = AppDatabase.getInstance(this);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//               db.transDao().delelteAll();
//            }
//
//        }).start();


        transViewModelProvider = new ViewModelProvider(this).get(ViewModelTrans.class);


        fragment = findViewById(R.id.add_transaction_fragment);

        fragment.setVisibility(View.GONE);

//        fragmentTitle = findViewById(R.id.select_fragment_title);
        cancelBtn = findViewById(R.id.new_transaction_cancel_btn);


        dateLayout = findViewById(R.id.new_transaction_date_layout);
        dateLayout.setOnClickListener(this);


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


        vat = findViewById(R.id.new_transaction_vat_switch);
        vat.setOnCheckedChangeListener(this);


        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                dateTime = myCalendar.getTimeInMillis();
                updateLabel();
            }
        };
        if (currentTransID>0){

            new Utlity.TaskRunner().executeAsync(new Utlity.GetTransCallable(this.getApplication(),currentTransID), (data) ->{
                TransactionModelView transDB = data;
                currentTrans = transDB;
                Log.v("trxID","trans ID : "+transDB.id);
              prepareLegal(transDB.legalEntry);
                prepareBusiness(transDB.businessUnit);
                prepareProject(transDB.project);
                prepareDepartment(transDB.department);
                String myFormat="yyyy-MM-dd";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ENGLISH);
                dateText.setText(dateFormat.format(transDB.date));

                vat.setChecked(transDB.isVat);
                dateTime=transDB.date;




            });
        }
else{
        dateTime = Calendar.getInstance().getTimeInMillis();
        prepareLegal(null);
        prepareBusiness(null);
        prepareDepartment(null);
        prepareProject(null);
    }

    }

    private void prepareLegal(String legalEntry) {
        legalText = findViewById(R.id.new_transaction_legal_select_title);

        legalSelectFragment = new SelectFragment(R.string.select_lagal, 0,legal_ID,legalText.getText().toString());

        legalLayout = findViewById(R.id.new_transaction_legal_layout);

        legalLayout.setOnClickListener(this);
        if (legalEntry == null) {
            String legalVal = sharedPref.getString(legal_ID, "MES");
            if (!legalVal.equals(""))
                legalText.setText(legalVal);
        }else {
            legalText.setText(legalEntry);
        }
        fragmentManager.beginTransaction()
                .add(R.id.add_transaction_fragment, legalSelectFragment, "legal")
                .commit();

    }


    private void prepareBusiness(String businessUnit) {
        businessText = findViewById(R.id.new_transaction_business_select_title);

        businessSelectFragment = new SelectFragment(R.string.select_business, R.drawable.outline_dashboard_customize_black_24,business_ID, businessText.getText().toString());

        businessLayout = findViewById(R.id.new_transaction_business_layout);

        businessLayout.setOnClickListener(this);
        if (businessUnit == null) {
            String businessVal = sharedPref.getString(business_ID, "MES-BU");
            if (!businessVal.equals(""))
                businessText.setText(businessVal);

        }else {
            businessText.setText(businessUnit);
        }
    }

    private void prepareProject(String project) {
        projectText = findViewById(R.id.new_transaction_project_select_title);


        projectSelectFragment = new SelectFragment(R.string.select_project, R.drawable.outline_dns_black_24, project_ID, projectText.getText().toString());

        projectLayout = findViewById(R.id.new_transaction_project_layout);

        projectLayout.setOnClickListener(this);
        if (project == null) {
            String projectVal = sharedPref.getString(project_ID, "M111");
            if (!projectVal.equals(""))
                projectText.setText(projectVal);
        }else {
            projectText.setText(project);
        }

    }

    private void prepareDepartment(String department) {
        depatmentText = findViewById(R.id.new_transaction_department_select_title);


        departmentLayout = findViewById(R.id.new_transaction_department_layout);

        departmentLayout.setOnClickListener(this);

        if (department == null) {
            String departmentVal = sharedPref.getString(department_ID, "BU1");
            if (!departmentVal.equals(""))
                depatmentText.setText(departmentVal);
            departmentSelectFragment = new SelectFragment(R.string.select_department, R.drawable.outline_account_tree_24, department_ID, depatmentText.getText().toString());
        }else {
            depatmentText.setText(department);
        }

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
                break;

            case R.id.new_transaction_confirm_btn:
                if (currentTransID>0){
                    Log.v("trxId", String.valueOf(currentTransID));
                    updateTransDB();
                }else {
                    Log.v("trxId", String.valueOf(currentTransID));

                    insertTransToDB();
                }

                break;

            case R.id.new_transaction_date_layout:
                new DatePickerDialog(AddTransaction.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

        }
    }

    private void updateTransDB() {
        String legalEntity, businessUnit, project, department, description;

        legalEntity = legalText.getText().toString();
        businessUnit = businessText.getText().toString();
        project = projectText.getText().toString();
        department = depatmentText.getText().toString();


        EditText descriptionEditText = findViewById(R.id.new_transaction_decription_edit_text);
        description = descriptionEditText.getEditableText().toString();

        TransactionModelView newTrans = new TransactionModelView(currentTransID,legalEntity, businessUnit, project, department, isVatChecked, dateTime, description, currentTrans.status,currentTrans.total_amount);
        new Utlity.TaskRunner().executeAsync(new Utlity.UpdateTransCallable(this.getApplication(), newTrans), (data) -> {
            Log.v("tSizeF", String.valueOf(data));
            transID = data;
            Intent toAddLines = new Intent(this, AddLine.class);
            toAddLines.putExtra(Utlity.transId, transID);
            startActivity(toAddLines);

        });
    }

    private void insertTransToDB() {

                    String legalEntity,businessUnit,project,department,description;

                    legalEntity = legalText.getText().toString();
                    businessUnit = businessText.getText().toString();
                    project = projectText.getText().toString();
                    department = depatmentText.getText().toString();


                    EditText descriptionEditText = findViewById(R.id.new_transaction_decription_edit_text);
                    description = descriptionEditText.getEditableText().toString();

                    TransactionModelView newTrans = new TransactionModelView(legalEntity,businessUnit,project,department,isVatChecked,dateTime,description,getString(R.string.incomplete));
        new Utlity.TaskRunner().executeAsync(new Utlity.AddTransCallable(this.getApplication(),newTrans),(data)->{
            Log.v("tSizeF",String.valueOf(data));
            transID = data;
            Intent toAddLines = new Intent(this, AddLine.class);
            toAddLines.putExtra(Utlity.transId, transID);
            startActivity(toAddLines);

        });
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, Utlity.CAMERA_REQUEST_ID);


//        databaseWriteExecutor.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.v("onBakcround","true");
//
//                        db.transDao().insertTransaction(newTrans);
//                        List<TransactionModelView> list = db.transDao().getAllNotLive();
//                        Log.v("onBakcround", String.valueOf(list.size()));
//
//
//                    }
//                });
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

    @Override
    public Object call() throws Exception {
        Log.v("inBack","finish");

        return null;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        isVatChecked=isChecked;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.v("pre0",permissions[0]+" = "+grantResults[0]);
        Log.v("pre1",permissions[1]+" = "+grantResults[1]);

        if ( grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.v("pre0",permissions[0]+" = "+grantResults[0]);
                if ( grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Log.v("pre1",permissions[1]+" = "+grantResults[1]);


                    if (requestCode == Utlity.CAMERA_REQUEST_ID) {

                    }
                }
            }
    }
}