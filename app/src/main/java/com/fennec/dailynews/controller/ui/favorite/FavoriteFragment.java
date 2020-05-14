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

    public static RecyclerView recyclerView;
    public static NewsTrendingAdapter newsAdapter;

    public static RecyclerView recyclerView2;
    public static CategoryAdapterHome CategoryAdapter;

    public static RecyclerView recyclerView3;
    public static NewsSuggestedAdapter newsSuggestedAdapter;

    public static ProgressDialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);

        main = this;

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

        return root;
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

        dialog.dismiss();
    }

    public static void to_newIntent(int id)
    {
        Intent i = new Intent(main.getContext(), NewsActivity.class);
        i.putExtra("id",id);
        main.startActivity(i);
    }
}