package com.fennec.dailynews.controller.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.fennec.dailynews.adapter.NewsAdapter;
import com.fennec.dailynews.config.Constante;
import com.fennec.dailynews.config.NewsJson;
import com.fennec.dailynews.controller.HomeActivity;
import com.fennec.dailynews.controller.NewsActivity;
import com.fennec.dailynews.repository.NewsRepository;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public static HomeFragment main;

    public static View root;

    public static RecyclerView recyclerView;
    public static NewsAdapter newsAdapter;

    public static ProgressDialog dialog;

    public static ShimmerFrameLayout shimmerContainer;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        root = inflater.inflate(R.layout.fragment_home, container, false);
        main = this;

        shimmerContainer = (ShimmerFrameLayout) root.findViewById(R.id.shimmer_view_container);
        shimmerContainer.startShimmerAnimation();


        /** clear data for update data **/
        NewsRepository.list_news.clear();

        /** get data for update data **/
        NewsJson newsJson = new NewsJson("news",inflater.getContext(), "get", 1);

        shimmerContainer.startShimmerAnimation();

        dialog = ProgressDialog.show(inflater.getContext(), "", "Loading data ...", true);


        return root;
    }

    public static void onSucces()
    {
        /** adapter for test we have to improve our self for this app  **/
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(main.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);

        newsAdapter = new NewsAdapter(NewsRepository.list_news);
        recyclerView.setAdapter(newsAdapter);
        /** adapter for test we have to improve our self for this end  **/

        dialog.dismiss();
        shimmerContainer.stopShimmerAnimation();
        shimmerContainer.setVisibility(View.GONE);
    }

    public static void to_newIntent(int id)
    {
        Intent i = new Intent(main.getContext(), NewsActivity.class);
        i.putExtra("id",id);
        main.startActivity(i);
    }
}