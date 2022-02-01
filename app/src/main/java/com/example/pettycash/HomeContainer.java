package com.example.pettycash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class HomeContainer extends AppCompatActivity implements View.OnClickListener {
    Fragment mainView , settingView ;
    ImageButton settings,home;
    RelativeLayout addTrnsaction;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_container);

        mainView = new HomeActivity();
        settingView = new SettingsFragmanet();

         fragmentManager = getSupportFragmentManager();


        fragmentManager.beginTransaction()
                .add(R.id.home_container_main_view,mainView,"home")
                .commit();

        settings = findViewById(R.id.home_container_button_bar_settings);
        home = findViewById(R.id.home_container_button_bar_home);
        addTrnsaction = findViewById(R.id.add_transaction_btn);

        settings.setOnClickListener(this);
        home.setOnClickListener(this);
        addTrnsaction.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Log.v("clicked",String.valueOf(view.getId()));

        switch (view.getId()){
            case R.id.home_container_button_bar_settings:
                fragmentManager.beginTransaction().replace(R.id.home_container_main_view,settingView)
                        .commit();

                Log.v("fList",fragmentManager.getFragments().get(0).toString());

                break;
            case R.id.home_container_button_bar_home:
//                fragmentManager.beginTransaction().remove(mainView).commit();

                fragmentManager.beginTransaction()
                        .replace(R.id.home_container_main_view,mainView)
                        .commit();
                break;

            case R.id.add_transaction_btn:
                Intent toTransaction = new Intent(getApplicationContext(),AddTransaction.class);
                startActivity(toTransaction);
                break;
        }

    }
}