package com.fennec.dailynews.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fennec.dailynews.R;
import com.fennec.dailynews.adapter.NewsSuggestedAdapter;
import com.fennec.dailynews.config.Constante;
import com.fennec.dailynews.config.ImageSaver;
import com.fennec.dailynews.entity.News;
import com.fennec.dailynews.repository.BookMarkRepository;
import com.fennec.dailynews.repository.CategoryRepository;
import com.fennec.dailynews.repository.NewsRepository;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BookNewsActivity extends AppCompatActivity {

    public static BookNewsActivity main;

    public static TextView title_news, title_des, time_news, nbr_comments, tv_category;

    public static Button btn_comment;

    public static ImageButton comment_image, tb_btn_bookmark, tb_btn_share;

    public static RecyclerView recyclerView;
    public static NewsSuggestedAdapter newsSuggestedAdapter;

    public static int idNews, acty;

    public static News current_news;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_news);

        main = this;

        idNews  = getIntent().getIntExtra("id",0);

        current_news = BookMarkRepository.getById(idNews);

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            title_des.setText(Html.fromHtml(current_news.description, Html.FROM_HTML_MODE_COMPACT));
        } else {
            title_des.setText(Html.fromHtml(current_news.description));
        }


        title_news.setText(current_news.title);
        time_news.setText(current_news.date_news);
        nbr_comments.setText(""+current_news.nbr_comments);

        btn_comment.setText("Read "+current_news.nbr_comments+" Comments");

        ImageView first_image = (ImageView) findViewById(R.id.first_image);
        //Glide.with(HomeActivity.main).load(Constante.url_host+"images/"+current_news.news_photo).centerCrop().into(first_image);

        ImageSaver imageSaver = new ImageSaver(main, "images", current_news.news_photo);
        first_image.setImageBitmap(imageSaver.load());

        Log.e("TAG-PHOTO", "onCreate: "+Constante.url_host+"images/"+current_news.news_photo);


        btn_comment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new SweetAlertDialog(main, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("No internet connection")
                        .show();
            }
        });

        nbr_comments.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new SweetAlertDialog(main, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("No internet connection")
                        .show();
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
                        .setContentText("Delete this news from your bookmark")
                        .setConfirmText("Yes")
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                        {
                            @Override
                            public void onClick(SweetAlertDialog sDialog)
                            {
                                sDialog.dismissWithAnimation();
                                BookMarkRepository.list_news.remove(current_news);
                                BookmarkActivity.update();

                                boolean isFilePresent = BookMarkRepository.isFilePresent(main, "storage.json");
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

                                main.finish();
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

}
