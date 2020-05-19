package com.fennec.dailynews.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.fennec.dailynews.R;
import com.fennec.dailynews.repository.UserRepository;

public class MainActivity extends AppCompatActivity {


    public static MainActivity main;

    public static String MY_PREFS_NAME = "first_log";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shimmer_test);
        main = this;

        isSharedPreferences();

        if(isNetworkConnected())
        {
            Intent intent = new Intent(main, HomeActivity.class);
            startActivity(intent);

            main.finish();
        }else {
            setContentView(R.layout.activity_main);

            Button btn_rafraichir = (Button) findViewById(R.id.btn_rafraichir);

            btn_rafraichir.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    main.finish();
                    startActivity(main.getIntent());
                }
            });
        }
    }

    private boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void isSharedPreferences()
    {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int id = prefs.getInt("id", 0);
        String email = prefs.getString("email", "vide");
        String name = prefs.getString("name", "vide");
        String password = prefs.getString("password", "vide");
        String status = prefs.getString("status", "vide");

        UserRepository.main_User.id = id;
        UserRepository.main_User.email = email;
        UserRepository.main_User.name = name;
        UserRepository.main_User.password = password;
        UserRepository.main_User.status = status;

        if(id == 0 && email.equals("vide"))
        {
            UserRepository.EXIST = false;
            Log.e("TAG-PREF", "isSharedPreferences: false"+email);
        }else {
            UserRepository.EXIST = true;
            Log.e("TAG-PREF", "isSharedPreferences: true"+email);
        }
    }
}
