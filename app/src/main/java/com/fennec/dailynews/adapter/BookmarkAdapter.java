package com.fennec.dailynews.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.fennec.dailynews.R;
import com.fennec.dailynews.config.Constante;
import com.fennec.dailynews.config.DateConfig;
import com.fennec.dailynews.controller.BookmarkActivity;
import com.fennec.dailynews.controller.HomeActivity;
import com.fennec.dailynews.controller.ui.category.CategoryFragment;
import com.fennec.dailynews.controller.ui.home.HomeFragment;
import com.fennec.dailynews.entity.Category;
import com.fennec.dailynews.entity.News;
import com.fennec.dailynews.repository.CategoryRepository;
import com.fennec.dailynews.repository.NewsRepository;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.MyViewHolder> {

    public List<News> list;
    public boolean showAdd = false;

    public int myViewType;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title_news,description_news,time_news,tv_nbr_comment,tv_category, tv_wname;
        public ImageView image_news, ifvideo;
        public View parent;
        public RecyclerView recyclerView;

        public MyViewHolder(View view)
        {
            super(view);
            parent=view;
            title_news = (TextView) view.findViewById(R.id.title_news);
            description_news = (TextView) view.findViewById(R.id.description_news);
            time_news = (TextView) view.findViewById(R.id.time_news);
            tv_nbr_comment = (TextView) view.findViewById(R.id.tv_nbr_comment);
            image_news = (ImageView) view.findViewById(R.id.image_news);
            tv_wname = (TextView) view.findViewById(R.id.tv_wname);

            ifvideo     = (ImageView) view.findViewById(R.id.ifvideo);

            tv_category = (TextView) view.findViewById(R.id.tv_category);
        }
    }

    public BookmarkAdapter(List<News> list)
    {
        this.list = list;
    }

    @Override
    public BookmarkAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);

        myViewType = viewType;

        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);


        return new BookmarkAdapter.MyViewHolder(itemView);
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
    public void onBindViewHolder(final BookmarkAdapter.MyViewHolder holder, final int position)
    {
        final News myNews = list.get(position);
        holder.title_news.setText(myNews.title);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            holder.description_news.setText(Html.fromHtml(myNews.description, Html.FROM_HTML_MODE_COMPACT));
        }else {
            holder.description_news.setText(Html.fromHtml(myNews.description));
        }


        holder.time_news.setText(DateConfig.parseDateToddMMyyyy(myNews.date_news));
        holder.tv_nbr_comment.setText(" "+myNews.nbr_comments);



        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(20));

        Glide.with(BookmarkActivity.main).load(Constante.url_images+"news/"+myNews.news_photo).apply(requestOptions).into(holder.image_news);

        Log.d("TAG_GLIDE", "onBindViewHolder: count");

        holder.ifvideo.setVisibility(View.GONE);

        if(myNews.content_type.equals("video"))
        {
            holder.ifvideo.setVisibility(View.VISIBLE);
        }

        holder.parent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                BookmarkActivity.to_newIntent(myNews.id);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
