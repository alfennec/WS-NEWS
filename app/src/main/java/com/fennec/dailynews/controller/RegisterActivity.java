package com.fennec.dailynews.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fennec.dailynews.R;

public class RegisterActivity extends AppCompatActivity {

    public static Button btn_login;

    public static RegisterActivity main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        main = this;

        /*btn_login =(Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(main, LoginActivity.class);
                main.startActivity(i);
            }
        });*/
    }
}
