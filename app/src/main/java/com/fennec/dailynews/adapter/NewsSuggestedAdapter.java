package com.fennec.dailynews.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.fennec.dailynews.R;
import com.fennec.dailynews.config.Constante;
import com.fennec.dailynews.controller.HomeActivity;
import com.fennec.dailynews.entity.News;

import java.util.List;

public class NewsSuggestedAdapter extends RecyclerView.Adapter<NewsSuggestedAdapter.MyViewHolder> {

    public List<News> list;
    public boolean showAdd = false;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title_news,time_news,nbr_comments;
        public ImageView image_news;
        public View parent;
        public RecyclerView recyclerView;

        public MyViewHolder(View view)
        {
            super(view);
            parent=view;
            title_news = (TextView) view.findViewById(R.id.title_news);
            time_news = (TextView) view.findViewById(R.id.time_news);
            nbr_comments = (TextView) view.findViewById(R.id.nbr_comments);
            image_news = (ImageView) view.findViewById(R.id.image_news);
        }
    }

    public NewsSuggestedAdapter(List<News> list)
    {
        this.list = list;
    }

    @Override
    public NewsSuggestedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_suggested, parent, false);

        return new NewsSuggestedAdapter.MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position)
    {
        if(position == getItemCount()-1 && showAdd)
            return 2;
        return 1;
    }

    @Override
    public void onBindViewHolder(final NewsSuggestedAdapter.MyViewHolder holder, final int position)
    {
        final News myNews = list.get(position);
        holder.title_news.setText(myNews.title);
        holder.time_news.setText(myNews.date_news);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(20));

        Glide.with(HomeActivity.main).load(Constante.url_host+"/images/"+myNews.news_photo).apply(requestOptions).into(holder.image_news);

        Log.d("TAG_GLIDE", "onBindViewHolder: count");

        holder.parent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                HomeActivity.to_newIntent();
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
