package com.example.pettycash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pettycash.Utality.App;
import com.example.pettycash.Utality.Utlity;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText userName , password;
    Button loginBtn;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                setContentView(R.layout.activity_main);
//
//            }
//        }, 3000);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

                    String checklegalVal = sharedPref.getString(UserPreferences.legal_ID,"");
                    if (! checklegalVal.equals("")) {
                        Intent toHome = new Intent(this, HomeContainer.class);
                        startActivity(toHome);
                        finish();

                    }

        userName = findViewById(R.id.login_userName);

        password = findViewById(R.id.login_password);

        loginBtn =  findViewById(R.id.login_loginBtn);

        forgotPassword = findViewById(R.id.login_forgotPassword);


        loginBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.login_loginBtn:
                String usernameValue = userName.getText().toString();
                boolean loginValidate = true;
                if (usernameValue.isEmpty()){
                    createToast(this,"please type the user name");
                    loginValidate = false;
                }
                String passwordValue = password.getText().toString();
                if (passwordValue.isEmpty()){
                    createToast(this,"please type the password");
                    loginValidate = false;
                }
                if (loginValidate){
                    createToast(this,"success");

//                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);;

//                    String checklegalVal = sharedPref.getString(UserPreferences.legal_ID,"");
//                    if (! checklegalVal.equals("")) {
//                        Intent toHome = new Intent(this, HomeContainer.class);
//                        startActivity(toHome);
//                        finish();
//                    }else {
                        Intent toPref = new Intent(this, UserPreferences.class);
                        startActivity(toPref);
                        finish();

                }

            case R.id.login_forgotPassword:




        }
    }

    public static void createToast(Context context , String str){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }
}