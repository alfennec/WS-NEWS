package com.fennec.dailynews.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fennec.dailynews.R;
import com.fennec.dailynews.adapter.NewsSuggestedAdapter;
import com.fennec.dailynews.entity.News;
import com.fennec.dailynews.repository.NewsRepository;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    public static EditText editText;

    public static ImageButton tb_btn_clear;


    public static  SearchActivity main;

    public static RecyclerView recyclerView;
    public static NewsSuggestedAdapter newsSuggestedAdapter;

    public static ImageView imageView3;
    public static TextView textView4,textView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        main = this;


        editText = (EditText) findViewById(R.id.autoCompleteTextView);
        tb_btn_clear = (ImageButton) findViewById(R.id.tb_btn_clear);
        tb_btn_clear.setVisibility(View.GONE);

        imageView3 = (ImageView) findViewById(R.id.imageView3);
        textView4  = (TextView) findViewById(R.id.textView4);
        textView5  = (TextView) findViewById(R.id.textView5);

        imageView3.setVisibility(View.GONE);
        textView4.setVisibility(View.GONE);
        textView5.setVisibility(View.GONE);



        editText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    performSearch(v.getText().toString());
                    tb_btn_clear.setVisibility(View.VISIBLE);

                    /** hide the keyboard **/
                    InputMethodManager inputManager = (InputMethodManager) main.getSystemService(main.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(main.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                    return true;
                }
                return false;
            }
        });

        tb_btn_clear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editText.setText("");

                /** hide the keyboard **/
                InputMethodManager inputManager = (InputMethodManager) main.getSystemService(main.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(main.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });
    }

    public static void performSearch(String keyword)
    {
        ArrayList<News> currentArray = NewsRepository.getKeyword(keyword);

        if(currentArray.size() == 0)
        {
            imageView3.setVisibility(View.VISIBLE);
            textView4.setVisibility(View.VISIBLE);
            textView5.setVisibility(View.VISIBLE);

            recyclerView = (RecyclerView) main.findViewById(R.id.recyclerView);
            LinearLayoutManager lm = new LinearLayoutManager(main, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(lm);

            newsSuggestedAdapter = new NewsSuggestedAdapter(currentArray);
            recyclerView.setAdapter(newsSuggestedAdapter);
        }else
            {
                imageView3.setVisibility(View.GONE);
                textView4.setVisibility(View.GONE);
                textView5.setVisibility(View.GONE);

                recyclerView = (RecyclerView) main.findViewById(R.id.recyclerView);
                LinearLayoutManager lm = new LinearLayoutManager(main, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(lm);

                newsSuggestedAdapter = new NewsSuggestedAdapter(currentArray);
                recyclerView.setAdapter(newsSuggestedAdapter);
            }
    }
}
