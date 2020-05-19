package com.fennec.dailynews.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.fennec.dailynews.R;
import com.fennec.dailynews.config.Constante;
import com.fennec.dailynews.controller.CommentsActivity;
import com.fennec.dailynews.controller.HomeActivity;
import com.fennec.dailynews.controller.ui.home.HomeFragment;
import com.fennec.dailynews.entity.News;
import com.fennec.dailynews.repository.CategoryRepository;

import java.util.List;

public class NewsTrendingAdapter extends RecyclerView.Adapter<NewsTrendingAdapter.MyViewHolder> {

    public List<News> list;
    public boolean showAdd = false;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title_news,tv_category;
        public ImageView image_news;
        public View parent;
        public RecyclerView recyclerView;

        public ImageButton comment_image, share_image;

        public MyViewHolder(View view)
        {
            super(view);
            parent=view;
            title_news  = (TextView) view.findViewById(R.id.title_news);
            image_news  = (ImageView) view.findViewById(R.id.image_news);
            tv_category = (TextView) view.findViewById(R.id.tv_category);

            comment_image   = (ImageButton) view.findViewById(R.id.comment_image);
            share_image     = (ImageButton) view.findViewById(R.id.share_image);
        }
    }

    public NewsTrendingAdapter(List<News> list)
    {
        this.list = list;
    }

    @Override
    public NewsTrendingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);

        View itemView;
        if(viewType == 1)
        {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_trendings, parent, false);
        }else{
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_trendings, parent, false);
        }

        return new NewsTrendingAdapter.MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position)
    {
        /*if(position == getItemCount()-1 && showAdd)
            return 2;
        return 1;*/

        if (position == 0) return 1;
        else return 2;
    }

    @Override
    public void onBindViewHolder(final NewsTrendingAdapter.MyViewHolder holder, final int position)
    {
        final News myNews = list.get(position);
        holder.title_news.setText(myNews.title);
        holder.tv_category.setText(CategoryRepository.getById(myNews.id_category).name);

        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius(8);
        shape.setColor(Color.parseColor(CategoryRepository.getById(myNews.id_category).bkcolor));

        holder.tv_category.setBackground(shape);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(20));

        Glide.with(HomeActivity.main).load(Constante.url_host+"/images/"+myNews.news_photo).apply(requestOptions).into(holder.image_news);

        Log.d("TAG_GLIDE", "onBindViewHolder: count");

        holder.comment_image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(HomeFragment.main.getContext(), CommentsActivity.class);
                i.putExtra("id",myNews.id);
                HomeFragment.main.getContext().startActivity(i);
            }
        });

        holder.share_image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, myNews.title);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, myNews.title+" \n \n " + myNews.description);
                HomeFragment.main.getContext().startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        holder.parent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                HomeFragment.to_newIntent(myNews.id);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
