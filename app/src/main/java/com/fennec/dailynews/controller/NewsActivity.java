package com.fennec.dailynews.controller;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.fennec.dailynews.adapter.NewsAdapter;
import com.fennec.dailynews.adapter.NewsSuggestedAdapter;
import com.fennec.dailynews.config.Constante;
import com.fennec.dailynews.repository.NewsRepository;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;

import com.fennec.dailynews.R;

public class NewsActivity extends AppCompatActivity {


    public NewsActivity main;

    public static RecyclerView recyclerView;
    public static NewsSuggestedAdapter newsSuggestedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        main = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Learning");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        ImageView first_image = (ImageView) findViewById(R.id.first_image);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(20));
        Glide.with(HomeActivity.main).load(Constante.url_host+"/images/nature.jpg").apply(requestOptions).into(first_image);


        /*** suggestion part **/

        /** adapter for test we have to improve our self for this app  **/
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(main, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);

        newsSuggestedAdapter = new NewsSuggestedAdapter(NewsRepository.list_news);
        recyclerView.setAdapter(newsSuggestedAdapter);
        /** adapter for test we have to improve our self for this end  **/

    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}
