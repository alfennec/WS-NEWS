package com.fennec.dailynews.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fennec.dailynews.adapter.NewsAdapter;
import com.fennec.dailynews.adapter.NewsSuggestedAdapter;
import com.fennec.dailynews.config.Constante;
import com.fennec.dailynews.config.ImageSaver;
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

import android.text.Html;
import android.transition.Transition;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.fennec.dailynews.R;

import java.net.URL;
import java.util.concurrent.ExecutionException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class NewsActivity extends AppCompatActivity {

    public static NewsActivity main;

    public static TextView title_news, title_des, time_news, nbr_comments, tv_category, tv_wname;

    public static Button btn_comment;

    public static ImageButton first_image, comment_image, tb_btn_bookmark, tb_btn_share;

    public static ImageView ifvideo;

    public static VideoView first_video;

    public static RecyclerView recyclerView;
    public static NewsSuggestedAdapter newsSuggestedAdapter;

    public static int idNews;

    public static News current_news;

    public static Bitmap newsImage;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        main = this;

        idNews  = getIntent().getIntExtra("id",0);

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
        tv_wname        = (TextView) findViewById(R.id.tv_wname);


        first_image     = (ImageButton) findViewById(R.id.first_image);
        btn_comment     = (Button) findViewById(R.id.btn_comment);
        comment_image   = (ImageButton) findViewById(R.id.comment_image);

        ifvideo         = (ImageView) findViewById(R.id.ifvideo);
        ifvideo.setVisibility(View.GONE);


        title_news.setText(current_news.title);
        title_des.setText(current_news.description);
        time_news.setText(current_news.date_news);
        tv_wname.setText(current_news.wname);
        nbr_comments.setText(""+current_news.nbr_comments);

        btn_comment.setText("Read "+current_news.nbr_comments+" Comments");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            title_des.setText(Html.fromHtml(current_news.description, Html.FROM_HTML_MODE_COMPACT));
        } else {
            title_des.setText(Html.fromHtml(current_news.description));
        }


        if(current_news.content_type.equals("video"))
        {
            ifvideo.setVisibility(View.VISIBLE);
        }


        tv_category.setText(CategoryRepository.getById(current_news.id_category).name);

        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius(8);
        shape.setColor(Color.parseColor(CategoryRepository.getById(current_news.id_category).bkcolor));
        tv_category.setBackground(shape);

        Glide.with(main).load(Constante.url_images+"news/"+current_news.news_photo).centerCrop().into(first_image);




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
                if(!BookMarkRepository.ifExist(current_news))
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
                                    BookMarkRepository.list_news.add(current_news);

                                    boolean isFilePresent = BookMarkRepository.isFilePresent(main, "storage.json");

                                    isFilePresent = true;

                                    Log.d("TAG-FILE", "storage.json je suis dehord "+isFilePresent);

                                    if(isFilePresent)
                                    {
                                        boolean isFileCreated = BookMarkRepository.create(main, "storage.json", BookMarkRepository.ArrayToJson());
                                        if(isFileCreated)
                                        {
                                            //proceed with storing the first todo  or show ui
                                            Log.d("TAG-FILE", "storage.json created " + BookMarkRepository.ArrayToJson());
                                        } else {
                                            //show error or try again.
                                            Log.d("TAG-FILE", "storage.json not created " + BookMarkRepository.ArrayToJson());
                                        }
                                    }

                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }else
                    {
                        new SweetAlertDialog(main, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("News already saved in the bookmark")
                                .show();
                    }


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

        first_image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(current_news.content_type.equals("video"))
                {
                    Intent intent = new Intent(main, VideoActivity.class);
                    intent.putExtra("id",current_news.id);
                    main.startActivity(intent);
                }else
                    {
                        Intent intent = new Intent(main, ImageActivity.class);
                        intent.putExtra("id",current_news.id);
                        main.startActivity(intent);
                    }
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
