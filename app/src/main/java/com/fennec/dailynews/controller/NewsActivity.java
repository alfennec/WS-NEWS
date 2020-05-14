package com.fennec.dailynews.controller;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.fennec.dailynews.adapter.NewsAdapter;
import com.fennec.dailynews.adapter.NewsSuggestedAdapter;
import com.fennec.dailynews.config.Constante;
import com.fennec.dailynews.entity.Category;
import com.fennec.dailynews.entity.News;
import com.fennec.dailynews.repository.CategoryRepository;
import com.fennec.dailynews.repository.NewsRepository;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fennec.dailynews.R;

public class NewsActivity extends AppCompatActivity {

    public static NewsActivity main;

    public static TextView title_news, title_des, time_news, nbr_comments;


    public static Button btn_comment;

    public static RecyclerView recyclerView;
    public static NewsSuggestedAdapter newsSuggestedAdapter;

    public static int idNews;

    public static News current_news;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        main = this;

        idNews = getIntent().getIntExtra("id",0);
        current_news = NewsRepository.getById(idNews);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Top News");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        title_news      = (TextView) findViewById(R.id.title_news);
        title_des       = (TextView) findViewById(R.id.title_des);
        time_news       = (TextView) findViewById(R.id.time_news);
        nbr_comments    = (TextView) findViewById(R.id.nbr_comments);


        btn_comment     = (Button) findViewById(R.id.btn_comment);

        title_news.setText(current_news.title);
        title_des.setText(current_news.description);
        time_news.setText(current_news.date_news);


        ImageView first_image = (ImageView) findViewById(R.id.first_image);

        //RequestOptions requestOptions = new RequestOptions();
        //requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(20));
        //Glide.with(HomeActivity.main).load(Constante.url_host+"images/"+current_news.news_photo).apply(requestOptions).into(first_image);

        Glide.with(HomeActivity.main).load(Constante.url_host+"images/"+current_news.news_photo).centerCrop().into(first_image);

        Log.e("TAG-PHOTO", "onCreate: "+Constante.url_host+"images/"+current_news.news_photo);


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

    public static void to_newIntent(int id)
    {
        Intent i = new Intent(main, NewsActivity.class);
        i.putExtra("id",id);
        main.startActivity(i);
    }
}
