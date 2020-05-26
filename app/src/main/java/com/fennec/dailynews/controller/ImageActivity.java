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

import com.bumptech.glide.Glide;
import com.fennec.dailynews.R;
import com.fennec.dailynews.config.Constante;
import com.fennec.dailynews.entity.News;
import com.fennec.dailynews.repository.NewsRepository;

import java.io.File;
import java.io.FileOutputStream;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ImageActivity extends AppCompatActivity {

    public static int idNews;
    public static News current_news;

    public static ImageActivity main;

    public static  ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        main = this;

        idNews  = getIntent().getIntExtra("id",0);
        current_news = NewsRepository.getById(idNews);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("News image");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        image = (ImageView) findViewById(R.id.image);

        Glide.with(main)
                .load(Constante.url_images+"news/"+current_news.news_photo)
                .error(R.drawable.back)
                .placeholder(R.drawable.back)
                .centerCrop()
                .into(image);

        ImageButton tb_btn_download = (ImageButton) findViewById(R.id.tb_btn_download);

        tb_btn_download.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //to get the image from the ImageView (say iv)
                BitmapDrawable draw = (BitmapDrawable) image.getDrawable();
                Bitmap bitmap = draw.getBitmap();

                FileOutputStream outStream = null;
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/DCIM/Daily-News");
                dir.mkdirs();
                String fileName = String.format("%d.jpg", System.currentTimeMillis());
                File outFile = new File(dir, fileName);
                try {
                    outStream = new FileOutputStream(outFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                }catch (Exception e){ Log.e("SAVE-PHOTO", "Error : "+e); }


                Log.e("SAVE-PHOTO", "SUCCESS  : passe ");

                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(dir));
                sendBroadcast(intent);

                new SweetAlertDialog(main, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Good job!")
                        .setContentText("Your image is saved tcheck your gallery")
                        .show();
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
