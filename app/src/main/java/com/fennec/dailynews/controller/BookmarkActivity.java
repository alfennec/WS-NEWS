package com.fennec.dailynews.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.fennec.dailynews.R;
import com.fennec.dailynews.adapter.BookmarkAdapter;
import com.fennec.dailynews.adapter.NewsAdapter;
import com.fennec.dailynews.repository.BookMarkRepository;
import com.fennec.dailynews.repository.NewsRepository;

public class BookmarkActivity extends AppCompatActivity {

    public static BookmarkActivity main;

    public static RecyclerView recyclerView;
    public static BookmarkAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        main = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bookmark");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(main, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);

        newsAdapter = new BookmarkAdapter(BookMarkRepository.list_news);
        recyclerView.setAdapter(newsAdapter);

    }

    public static void update()
    {
        LinearLayoutManager lm = new LinearLayoutManager(main, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);

        newsAdapter = new BookmarkAdapter(BookMarkRepository.list_news);
        recyclerView.setAdapter(newsAdapter);
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    public static void to_newIntent(int id)
    {
        Intent i = new Intent(main, BookNewsActivity.class);
        i.putExtra("id",id);
        main.startActivity(i);
    }
}
