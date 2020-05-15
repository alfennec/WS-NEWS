package com.fennec.dailynews.controller.ui.favorite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fennec.dailynews.R;
import com.fennec.dailynews.adapter.CategoryAdapterHome;
import com.fennec.dailynews.adapter.NewsAdapter;
import com.fennec.dailynews.adapter.NewsSuggestedAdapter;
import com.fennec.dailynews.adapter.NewsTrendingAdapter;
import com.fennec.dailynews.config.NewsJson;
import com.fennec.dailynews.controller.NewsActivity;
import com.fennec.dailynews.controller.ui.home.HomeFragment;
import com.fennec.dailynews.repository.CategoryRepository;
import com.fennec.dailynews.repository.NewsRepository;

public class FavoriteFragment extends Fragment {

    private FavoriteViewModel favoriteViewModel;

    public static FavoriteFragment main;
    public static View root;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);

        main = this;



        return root;
    }

    public static void to_newIntent(int id)
    {
        Intent i = new Intent(main.getContext(), NewsActivity.class);
        i.putExtra("id",id);
        main.startActivity(i);
    }
}