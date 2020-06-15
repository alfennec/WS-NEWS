package com.fennec.dailynews.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fennec.dailynews.R;

public class BugActivity extends AppCompatActivity {

    public static BugActivity main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug);

        main = this;

        Button btn_rafraichir = (Button) findViewById(R.id.btn_rafraichir);

        btn_rafraichir.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(main, HomeActivity.class);
                startActivity(intent);

                main.finish();
            }
        });
    }
}
