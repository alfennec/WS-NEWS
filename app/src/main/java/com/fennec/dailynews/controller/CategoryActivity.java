package com.fennec.dailynews.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fennec.dailynews.R;
import com.fennec.dailynews.adapter.NewsAdapter;
import com.fennec.dailynews.entity.Category;
import com.fennec.dailynews.repository.CategoryRepository;
import com.fennec.dailynews.repository.NewsRepository;

public class CategoryActivity extends AppCompatActivity {

    public static CategoryActivity main;

    public static RecyclerView recyclerView;
    public static NewsAdapter newsAdapter;

    public static int idCat;

    public static Category current_category;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static ImageButton tb_btn_search;

    public static TextView tv_nonews;
    public static ImageView im_nonews;

    public int CountExist = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        main = this;

        idCat = getIntent().getIntExtra("id",0);

        current_category = CategoryRepository.getById(idCat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(current_category.name);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(main, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);

        if(idCat == 0)
        {
            newsAdapter = new NewsAdapter(NewsRepository.list_news);

            CountExist = NewsRepository.list_news.size();
        }else
            {
                newsAdapter = new NewsAdapter(NewsRepository.getNewsWhereIdCat(idCat));

                CountExist = NewsRepository.getNewsWhereIdCat(idCat).size();
            }
        recyclerView.setAdapter(newsAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                Toast.makeText(main, "Refresh", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        mSwipeRefreshLayout.setColorScheme(R.color.blue, R.color.purple, R.color.green, R.color.orange);


        tb_btn_search = (ImageButton) findViewById(R.id.tb_btn_search);

        tb_btn_search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(main, SearchActivity.class);
                main.startActivity(i);
            }
        });




        tv_nonews = (TextView) findViewById(R.id.tv_nonews);
        im_nonews = (ImageView) findViewById(R.id.im_nonews);

        tv_nonews.setVisibility(View.GONE);
        im_nonews.setVisibility(View.GONE);

        if(CountExist == 0)
        {
            tv_nonews.setVisibility(View.VISIBLE);
            im_nonews.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}
