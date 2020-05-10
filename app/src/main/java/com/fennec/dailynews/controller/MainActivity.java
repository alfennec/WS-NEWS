package com.fennec.dailynews.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.fennec.dailynews.R;

public class MainActivity extends AppCompatActivity {


    public static MainActivity main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shimmer_test);
        main = this;

        //ShimmerFrameLayout shimmerContainer = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        //shimmerContainer.startShimmerAnimation();

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
}
