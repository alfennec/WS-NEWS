package com.fennec.dailynews.controller.ui.category;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fennec.dailynews.R;
import com.fennec.dailynews.adapter.CategoryAdapter;
import com.fennec.dailynews.adapter.NewsAdapter;
import com.fennec.dailynews.config.CategoryJson;
import com.fennec.dailynews.config.Constante;
import com.fennec.dailynews.config.NewsJson;
import com.fennec.dailynews.controller.BugActivity;
import com.fennec.dailynews.controller.CategoryActivity;
import com.fennec.dailynews.controller.HomeActivity;
import com.fennec.dailynews.controller.NewsActivity;
import com.fennec.dailynews.controller.ui.home.HomeFragment;
import com.fennec.dailynews.repository.CategoryRepository;
import com.fennec.dailynews.repository.NewsRepository;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CategoryFragment extends Fragment {

    private CategoryViewModel categoryViewModel;

    public static CategoryFragment main;

    public static View root;

    public static RecyclerView recyclerView;
    public static CategoryAdapter categoryAdapter;

    public static SweetAlertDialog pDialog;

    public static ShimmerFrameLayout shimmerContainer;

    private SwipeRefreshLayout swipeContainer;

    public static int time_dialog = 5000;
    public static boolean server_stats = false;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        root = inflater.inflate(R.layout.fragment_category, container, false);

        main = this;

        //shimmerContainer = (ShimmerFrameLayout) root.findViewById(R.id.shimmer_view_container);
        //shimmerContainer.startShimmerAnimation();


        /** clear data for update data **/
        CategoryRepository.list_category.clear();

        /** get data for update data **/
        CategoryJson categoryJson = new CategoryJson("category",inflater.getContext(), "get", 1);

        pDialog = new SweetAlertDialog(main.getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#3483fb"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if(!server_stats)
                {
                    pDialog.dismiss();

                    Intent intent = new Intent(main.getContext(), BugActivity.class);
                    startActivity(intent);

                    HomeActivity.main.finish();
                }
            }
        }, time_dialog);

        return root;
    }

    public static void onSucces()
    {
        server_stats = true;

        /** adapter for test we have to improve our self for this app  **/
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(main.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(new GridLayoutManager(main.main.getContext(), 2));


        categoryAdapter = new CategoryAdapter(CategoryRepository.list_category);
        recyclerView.setAdapter(categoryAdapter);
        /** adapter for test we have to improve our self for this end  **/

        pDialog.dismiss();
        //shimmerContainer.stopShimmerAnimation();
        //shimmerContainer.setVisibility(View.GONE);
    }

    public static void to_newIntent(int id)
    {
        Intent i = new Intent(main.getContext(), CategoryActivity.class);
        i.putExtra("id",id);
        main.startActivity(i);
    }
}