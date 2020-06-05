package com.fennec.dailynews.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fennec.dailynews.R;
import com.fennec.dailynews.adapter.CommentAdapter;
import com.fennec.dailynews.adapter.NewsTrendingAdapter;
import com.fennec.dailynews.config.CategoryJson;
import com.fennec.dailynews.config.CommentsJson;
import com.fennec.dailynews.entity.Comments;
import com.fennec.dailynews.entity.News;
import com.fennec.dailynews.repository.CategoryRepository;
import com.fennec.dailynews.repository.CommentsRepository;
import com.fennec.dailynews.repository.NewsRepository;
import com.fennec.dailynews.repository.UserRepository;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CommentsActivity extends AppCompatActivity {

    public static CommentsActivity main;

    public static int idNews;

    public static RecyclerView recyclerView;
    public static CommentAdapter commentsAdapter;

    public static ProgressDialog dialog;
    public static SweetAlertDialog pDialog;

    public static TextView tv_nbr_comment,tv_title;

    public static ImageButton btn_add_comment;

    public static EditText add_comment;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        main = this;

        idNews = getIntent().getIntExtra("id",0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Comments");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        tv_nbr_comment = (TextView) findViewById(R.id.tv_nbr_comment);
        tv_title = (TextView) findViewById(R.id.tv_title);

        tv_title.setText(NewsRepository.getById(idNews).title);


        /** clear data for update data **/
        CommentsRepository.list_comments.clear();

        /** get data for update data **/

        CommentsJson commentsJson = new CommentsJson("comments/"+idNews, main, "GET", 1, new Comments());

        pDialog = new SweetAlertDialog(main, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#3483fb"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();


        /**** add comment section  not finished -------------------------- ***/


        add_comment = (EditText) findViewById(R.id.add_comment);
        btn_add_comment = (ImageButton) findViewById(R.id.btn_add_comment);

        btn_add_comment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(UserRepository.EXIST)
                {
                    String comment = add_comment.getText().toString();
                    add_comment.setText("");

                    Comments comm = new Comments();
                    comm.id_news = idNews;

                    //CommentsRepository.list_comments.add(new Comments(""+UserRepository.main_User.name, idNews, comment, "2019-05-05 01:19:14", "2019-05-05 01:19:14"));
                    //onUpdate();

                    CommentsJson commentsJson = new CommentsJson("comments", main, "POST", 2, new Comments(""+UserRepository.main_User.id, idNews, comment));

                    pDialog = new SweetAlertDialog(main, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#3483fb"));
                    pDialog.setTitleText("Loading");
                    pDialog.setCancelable(false);
                    pDialog.show();

                }else
                    {
                        Intent intent = new Intent(main, LoginActivity.class);
                        intent.putExtra("acty", 2);
                        main.startActivity(intent);
                    }
            }
        });
    }

    public static void onSucces()
    {

        /** adapter for test we have to improve our self for this app  **/
        recyclerView = (RecyclerView) main.findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(main, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);

        commentsAdapter = new CommentAdapter(CommentsRepository.list_comments);
        recyclerView.setAdapter(commentsAdapter);

        recyclerView.setNestedScrollingEnabled(false);
        /** adapter for test we have to improve our self for this end  **/

        tv_nbr_comment.setText(""+CommentsRepository.list_comments.size());

        pDialog.dismiss();
    }

    public static void onUpdate()
    {
        /** adapter for test we have to improve our self for this app  **/
        recyclerView = (RecyclerView) main.findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(main, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);

        commentsAdapter = new CommentAdapter(CommentsRepository.list_comments);
        recyclerView.setAdapter(commentsAdapter);

        recyclerView.setNestedScrollingEnabled(false);
        /** adapter for test we have to improve our self for this end  **/


        tv_nbr_comment.setText(""+CommentsRepository.list_comments.size());
    }

    public static void OnSuccesPost()
    {
        /** clear data for update data **/
        CommentsRepository.list_comments.clear();

        /** get data for update data **/

        CommentsJson commentsJson = new CommentsJson("comments/"+idNews, main, "GET", 1, new Comments());
    }

    public static void OnFailedPost()
    {
        Toast.makeText(main, "Faild to set your comment", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}
