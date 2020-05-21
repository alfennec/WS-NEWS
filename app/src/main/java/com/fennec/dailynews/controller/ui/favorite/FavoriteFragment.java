package com.fennec.dailynews.controller.ui.favorite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fennec.dailynews.R;
import com.fennec.dailynews.adapter.BookmarkAdapter;
import com.fennec.dailynews.adapter.CategoryAdapterHome;
import com.fennec.dailynews.adapter.NewsAdapter;
import com.fennec.dailynews.adapter.NewsSuggestedAdapter;
import com.fennec.dailynews.adapter.NewsTrendingAdapter;
import com.fennec.dailynews.config.NewsJson;
import com.fennec.dailynews.controller.BookNewsActivity;
import com.fennec.dailynews.controller.NewsActivity;
import com.fennec.dailynews.controller.ui.home.HomeFragment;
import com.fennec.dailynews.repository.BookMarkRepository;
import com.fennec.dailynews.repository.CategoryRepository;
import com.fennec.dailynews.repository.NewsRepository;

public class FavoriteFragment extends Fragment {

    private FavoriteViewModel favoriteViewModel;

    public static FavoriteFragment main;
    public static View root;

    public static RecyclerView recyclerView;
    public static NewsSuggestedAdapter newsAdapter;

    public static ImageView imageView3;

    public static TextView textView4, textView5;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        root = inflater.inflate(R.layout.fragment_favorite, container, false);

        main = this;

        /** adapter for test we have to improve our self for this app  **/
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(main.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);

        newsAdapter = new NewsSuggestedAdapter(BookMarkRepository.list_news);
        recyclerView.setAdapter(newsAdapter);

        recyclerView.setNestedScrollingEnabled(false);
        /** adapter for test we have to improve our self for this end  **/



        imageView3  = (ImageView) root.findViewById(R.id.imageView3);

        textView4   = (TextView) root.findViewById(R.id.textView4);
        textView5   = (TextView) root.findViewById(R.id.textView5);

        imageView3.setVisibility(View.GONE);
        textView4.setVisibility(View.GONE);
        textView5.setVisibility(View.GONE);

        if(BookMarkRepository.list_news.size() == 0)
        {
            imageView3.setVisibility(View.VISIBLE);
            textView4.setVisibility(View.VISIBLE);
            textView5.setVisibility(View.VISIBLE);
        }

        return root;
    }

    public static void to_newIntent(int id)
    {
        Intent i = new Intent(main.getContext(), BookNewsActivity.class);
        i.putExtra("id",id);
        main.startActivity(i);
    }

    public static void update()
    {
        LinearLayoutManager lm = new LinearLayoutManager(main.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);

        newsAdapter = new NewsSuggestedAdapter(BookMarkRepository.list_news);
        recyclerView.setAdapter(newsAdapter);
    }
}