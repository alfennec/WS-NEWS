package com.fennec.dailynews.controller.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.fennec.dailynews.repository.NewsRepository;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public static HomeFragment main;

    public static View root;

    public static RecyclerView recyclerView;
    public static NewsAdapter newsAdapter;

    public static ProgressDialog dialog;

    public ImageView first_image;

    public static ShimmerFrameLayout shimmerContainer;

    private SwipeRefreshLayout swipeContainer;


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


        first_image = (ImageView) root.findViewById(R.id.first_image);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(20));
        Glide.with(HomeActivity.main).load(Constante.url_host+"/images/nature.jpg").apply(requestOptions).into(first_image);



        swipeContainer = (SwipeRefreshLayout) root.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                //fetchTimelineAsync(0);
                Toast.makeText(HomeActivity.main,"success !!",Toast.LENGTH_LONG).show();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


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
}