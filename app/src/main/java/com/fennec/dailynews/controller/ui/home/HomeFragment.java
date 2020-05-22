package com.fennec.dailynews.controller.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fennec.dailynews.R;
import com.fennec.dailynews.adapter.CategoryAdapterHome;
import com.fennec.dailynews.adapter.NewsAdapter;
import com.fennec.dailynews.adapter.NewsSuggestedAdapter;
import com.fennec.dailynews.adapter.NewsTrendingAdapter;
import com.fennec.dailynews.config.CategoryJson;
import com.fennec.dailynews.config.Constante;
import com.fennec.dailynews.config.NewsJson;
import com.fennec.dailynews.controller.HomeActivity;
import com.fennec.dailynews.controller.NewsActivity;
import com.fennec.dailynews.repository.CategoryRepository;
import com.fennec.dailynews.repository.NewsRepository;
import com.fennec.dailynews.repository.UserRepository;
import com.fennec.dailynews.repository.WeatherRepository;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public static HomeFragment main;

    public static View root;

    public static RecyclerView recyclerView;
    public static NewsTrendingAdapter newsAdapter;

    public static RecyclerView recyclerView2;
    public static CategoryAdapterHome CategoryAdapter;

    public static RecyclerView recyclerView3;
    public static NewsSuggestedAdapter newsSuggestedAdapter;

    public static ProgressDialog dialog;
    public static SweetAlertDialog pDialog;

    public static ShimmerFrameLayout shimmerContainer;

    public static ImageView weather_img;
    public static TextView tv_temp, tv_firstText;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        root = inflater.inflate(R.layout.fragment_home, container, false);
        main = this;

        shimmerContainer = (ShimmerFrameLayout) root.findViewById(R.id.shimmer_view_container);
        shimmerContainer.startShimmerAnimation();

        /***** set the weather **/

        tv_firstText = (TextView) root.findViewById(R.id.tv_firstText);

        SimpleDateFormat  sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        if(Convert24to12(currentDateandTime).contains("AM"))
        {
            if(!UserRepository.EXIST)
            {
                tv_firstText.setText("Morning, Dear");
            }else {
                 tv_firstText.setText("Morning, "+UserRepository.main_User.fname);
            }
        }else{
            if(!UserRepository.EXIST)
            {
                tv_firstText.setText("Evening, Dear");
            }else {
                tv_firstText.setText("Evening, "+UserRepository.main_User.fname);
            }
        }

        weather_img = (ImageView) root.findViewById(R.id.weather_img);
        tv_temp = (TextView) root.findViewById(R.id.tv_temp);

        //Glide.with(HomeActivity.main).load(Constante.url_host+"images/weather/"+ WeatherRepository.img +".png").into(weather_img);
        //tv_temp.setText(WeatherRepository.temp+"°C");

        Glide.with(HomeActivity.main).load(Constante.url_host+"images/weather/02n.png").into(weather_img);
        tv_temp.setText("25°C");




        /** clear data for update data **/
        NewsRepository.list_news.clear();
        CategoryRepository.list_category.clear();

        /** get data for update data **/

        CategoryJson categoryJson = new CategoryJson("category",inflater.getContext(), "get", 2);

        shimmerContainer.startShimmerAnimation();
       //dialog = ProgressDialog.show(inflater.getContext(), "", "Loading data ...", true);

        pDialog = new SweetAlertDialog(main.getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#3483fb"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();


        return root;
    }

    public static void onSuccesCategory()
    {
        NewsJson newsJson = new NewsJson("news", main.getContext(), "get", 1);
    }

    public static void onSucces()
    {
        /** adapter for test we have to improve our self for this app  **/
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(main.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(lm);

        newsAdapter = new NewsTrendingAdapter(NewsRepository.list_news);
        recyclerView.setAdapter(newsAdapter);
        /** adapter for test we have to improve our self for this end  **/

        /** adapter for test we have to improve our self for this app  **/
        recyclerView2 = (RecyclerView) root.findViewById(R.id.recyclerView2);
        lm = new LinearLayoutManager(main.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(lm);

        CategoryAdapter = new CategoryAdapterHome(CategoryRepository.list_category);
        recyclerView2.setAdapter(CategoryAdapter);

        /** adapter for test we have to improve our self for this end  **/

        /** adapter for test we have to improve our self for this app  **/
        recyclerView3 = (RecyclerView) root.findViewById(R.id.recyclerView3);
        lm = new LinearLayoutManager(main.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView3.setLayoutManager(lm);

        newsSuggestedAdapter = new NewsSuggestedAdapter(NewsRepository.list_news);
        recyclerView3.setAdapter(newsSuggestedAdapter);

        recyclerView3.setNestedScrollingEnabled(false);
        /** adapter for test we have to improve our self for this end  **/

        pDialog.dismiss();
        shimmerContainer.stopShimmerAnimation();
        shimmerContainer.setVisibility(View.GONE);
    }

    public static void to_newIntent(int id)
    {
        Intent i = new Intent(main.getContext(), NewsActivity.class);
        i.putExtra("id",id);
        main.startActivity(i);
    }

    public static void updateRecycle(int idCat)
    {
        if(idCat == 0)
        {
            /** adapter for test we have to improve our self for this app  **/
            recyclerView3 = (RecyclerView) root.findViewById(R.id.recyclerView3);
            LinearLayoutManager lm = new LinearLayoutManager(main.getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView3.setLayoutManager(lm);

            newsSuggestedAdapter = new NewsSuggestedAdapter(NewsRepository.list_news);
            recyclerView3.setAdapter(newsSuggestedAdapter);

            recyclerView3.setNestedScrollingEnabled(false);
            /** adapter for test we have to improve our self for this end  **/
        }else
            {
                /** adapter for test we have to improve our self for this app  **/
                recyclerView3 = (RecyclerView) root.findViewById(R.id.recyclerView3);
                LinearLayoutManager lm = new LinearLayoutManager(main.getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView3.setLayoutManager(lm);

                newsSuggestedAdapter = new NewsSuggestedAdapter(NewsRepository.getNewsWhereIdCat(idCat));
                recyclerView3.setAdapter(newsSuggestedAdapter);

                recyclerView3.setNestedScrollingEnabled(false);
                /** adapter for test we have to improve our self for this end  **/
            }
    }

    public static String Convert24to12(String time)
    {
        String convertedTime ="";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = parseFormat.parse(time);
            convertedTime=displayFormat.format(date);
            System.out.println("convertedTime : "+convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
        //Output will be 10:23 PM
    }
}