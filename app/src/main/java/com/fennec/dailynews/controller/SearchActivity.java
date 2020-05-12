package com.fennec.dailynews.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

import com.fennec.dailynews.R;
import com.fennec.dailynews.adapter.NewsSuggestedAdapter;
import com.fennec.dailynews.repository.NewsRepository;

public class SearchActivity extends AppCompatActivity {

    public static AutoCompleteTextView autoCompleteTextView;

    public static ImageButton tb_btn_search;


    public static  SearchActivity main;

    public static RecyclerView recyclerView;
    public static NewsSuggestedAdapter newsSuggestedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        main = this;


        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        ArrayAdapter adapterNews = new ArrayAdapter(this,android.R.layout.simple_list_item_1, NewsRepository.getStringNews());
        autoCompleteTextView.setAdapter(adapterNews);
        autoCompleteTextView.setDropDownBackgroundDrawable(new ColorDrawable(main.getResources().getColor(R.color.colorBlanc)));

        tb_btn_search = (ImageButton) findViewById(R.id.tb_btn_search);

        tb_btn_search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*** suggestion part **/

                /** adapter for test we have to improve our self for this app  **/
                recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                LinearLayoutManager lm = new LinearLayoutManager(main, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(lm);

                newsSuggestedAdapter = new NewsSuggestedAdapter(NewsRepository.list_news);
                recyclerView.setAdapter(newsSuggestedAdapter);
                /** adapter for test we have to improve our self for this end  **/
            }
        });
    }
}
