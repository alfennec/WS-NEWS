package com.fennec.dailynews.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.fennec.dailynews.R;
import com.fennec.dailynews.config.Constante;
import com.fennec.dailynews.entity.News;
import com.fennec.dailynews.repository.NewsRepository;

import java.io.File;
import java.io.FileOutputStream;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class VideoActivity extends AppCompatActivity {

    public static int idNews;
    public static News current_news;

    public static VideoActivity main;

    public static VideoView video;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        main = this;

        idNews  = getIntent().getIntExtra("id",0);
        current_news = NewsRepository.getById(idNews);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("News video");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        video = (VideoView) findViewById(R.id.video);
        video.setVideoURI(Uri.parse(Constante.url_images+"news/"+current_news.news_photo));


        // create an object of media controller
        MediaController mediaController = new MediaController(this);
        // set media controller object for a video view
        video.setMediaController(mediaController);

        video.start();
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}
