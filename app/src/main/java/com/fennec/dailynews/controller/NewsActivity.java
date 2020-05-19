package com.fennec.dailynews.controller;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.fennec.dailynews.adapter.NewsAdapter;
import com.fennec.dailynews.adapter.NewsSuggestedAdapter;
import com.fennec.dailynews.config.Constante;
import com.fennec.dailynews.entity.Category;
import com.fennec.dailynews.entity.News;
import com.fennec.dailynews.repository.BookMarkRepository;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fennec.dailynews.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class NewsActivity extends AppCompatActivity {

    public static NewsActivity main;

    public static TextView title_news, title_des, time_news, nbr_comments, tv_category;

    public static Button btn_comment;

    public static ImageButton comment_image, tb_btn_bookmark, tb_btn_share;

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
        getSupportActionBar().setTitle(CategoryRepository.getById(current_news.id_category).name);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        title_news      = (TextView) findViewById(R.id.title_news);
        title_des       = (TextView) findViewById(R.id.title_des);
        time_news       = (TextView) findViewById(R.id.time_news);
        nbr_comments    = (TextView) findViewById(R.id.nbr_comments);
        tv_category     = (TextView) findViewById(R.id.tv_category);


        btn_comment     = (Button) findViewById(R.id.btn_comment);
        comment_image   = (ImageButton) findViewById(R.id.comment_image);

        title_news.setText(current_news.title);
        title_des.setText(current_news.description);
        time_news.setText(current_news.date_news);
        nbr_comments.setText(""+current_news.nbr_comments);

        tv_category.setText(CategoryRepository.getById(current_news.id_category).name);


        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius(8);
        shape.setColor(Color.parseColor(CategoryRepository.getById(current_news.id_category).bkcolor));

        tv_category.setBackground(shape);


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

        newsSuggestedAdapter = new NewsSuggestedAdapter(NewsRepository.getNewsWhereIdCat(current_news.id_category));
        recyclerView.setAdapter(newsSuggestedAdapter);
        /** adapter for test we have to improve our self for this end  **/

        comment_image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(main, CommentsActivity.class);
                i.putExtra("id",idNews);
                main.startActivity(i);
            }
        });

        btn_comment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(main, CommentsActivity.class);
                i.putExtra("id",idNews);
                main.startActivity(i);
            }
        });


        /**** toolbar part(){} **/

        tb_btn_bookmark = (ImageButton) findViewById(R.id.tb_btn_bookmark);
        tb_btn_share    = (ImageButton) findViewById(R.id.tb_btn_share);

        tb_btn_bookmark.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new SweetAlertDialog(main, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Add the news into your bookmark")
                        .setConfirmText("Yes")
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                        {
                            @Override
                            public void onClick(SweetAlertDialog sDialog)
                            {
                                sDialog.dismissWithAnimation();

                                BookMarkRepository.list_news.add(current_news);


                            }
                        })
                        .show();
            }
        });

        tb_btn_share.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, current_news.title);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, current_news.title+" \n \n " + current_news.description);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

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
