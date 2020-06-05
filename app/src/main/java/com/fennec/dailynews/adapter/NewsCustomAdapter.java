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
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.fennec.dailynews.R;
import com.fennec.dailynews.adapter.OnLoadMoreListener;
import com.fennec.dailynews.config.Constante;
import com.fennec.dailynews.config.DateConfig;
import com.fennec.dailynews.controller.CommentsActivity;
import com.fennec.dailynews.controller.HomeActivity;
import com.fennec.dailynews.controller.ui.home.HomeFragment;
import com.fennec.dailynews.entity.News;
import com.fennec.dailynews.repository.CategoryRepository;


import java.util.List;

public class NewsCustomAdapter extends RecyclerView.Adapter {

    // Matching each View Type against a Integer
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    // Items List varaiable
    private List<News> mDataset;

    // Boolean to track loading status
    private boolean loading = false;

    // Listener or the Interface defined in STEP 4
    private OnLoadMoreListener mOnLoadMoreListener;

    // ViewHolder for Comment or the Items to be shown in RecyclerView
    // You must define it according to the data you want to show



    public static class DataViewHolder extends RecyclerView.ViewHolder
    {

        public TextView title_news,time_news,nbr_comments,tv_category;
        public ImageView image_news;
        public View parent;
        public RecyclerView recyclerView;

        public ImageButton comment_image;

        public DataViewHolder(View view) {
            super(view);
            parent=view;
            title_news = (TextView) view.findViewById(R.id.title_news);
            time_news = (TextView) view.findViewById(R.id.time_news);
            nbr_comments = (TextView) view.findViewById(R.id.nbr_comments);
            image_news = (ImageView) view.findViewById(R.id.image_news);
            tv_category = (TextView) view.findViewById(R.id.tv_category);

            comment_image = (ImageButton) view.findViewById(R.id.comment_image);
        }

    }

    // ViewHolder for ProgressBar
    // Copy it as it is
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressbar);
        }

    }

    // Constructor for the Adapter
    public NewsCustomAdapter(List<News> Dataset, OnLoadMoreListener onLoadMoreListener)
    {
        mDataset = Dataset;
        mOnLoadMoreListener = onLoadMoreListener;
    }

    // OnCreateViewHolder, where we are creating the ViewHolder as per the ViewType
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_suggested, parent, false);
            vh = new DataViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_progress, parent, false);
            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    // Binding data to a ViewHolder as per its ViewType
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof DataViewHolder)
        {
            final News myNews = mDataset.get(position);
            ((DataViewHolder) holder).title_news.setText(myNews.title);
            ((DataViewHolder) holder).time_news.setText(DateConfig.parseDateToddMMyyyy(myNews.date_news));
            ((DataViewHolder) holder).nbr_comments.setText(" "+myNews.nbr_comments);
            ((DataViewHolder) holder).tv_category.setText(CategoryRepository.getById(myNews.id_category).name);


            GradientDrawable shape =  new GradientDrawable();
            shape.setCornerRadius(8);
            shape.setColor(Color.parseColor(CategoryRepository.getById(myNews.id_category).bkcolor));

            ((DataViewHolder) holder).tv_category.setBackground(shape);


            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(20));

            Glide.with(HomeActivity.main).load(Constante.url_images+"/news/"+myNews.news_photo).apply(requestOptions).into(((DataViewHolder) holder).image_news);

            Log.d("TAG_GLIDE", "onBindViewHolder: count");

            ((DataViewHolder) holder).comment_image.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(HomeFragment.main.getContext(), CommentsActivity.class);
                    i.putExtra("id",myNews.id);
                    HomeFragment.main.getContext().startActivity(i);
                }
            });

            ((DataViewHolder) holder).parent.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    HomeFragment.to_newIntent(myNews.id);
                }
            });

        } else {

            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
            if (!loading) {
                // End has been reached
                // Do something
                if (mOnLoadMoreListener != null)
                {
                    mOnLoadMoreListener.onLoadMore(position);
                }
                loading = true;
            }

        }
    }

    // Method to set value of boolean variable "loading" to false
    // this method is called when data is loaded in the Activity class
    public void setLoaded() {
        loading = false;
    }

    // This method contains the main logic for showing ProgressBar at the end of RecyclerView
    // In the Activity class, we insert a null item at the end of the list
    // this null item produces a ProgressViewHolder by the logic given below
    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // Method to add more items to the list
    public void update(List<News> newItems)
    {
        mDataset.addAll(newItems);
        notifyDataSetChanged();
    }

    // This method is used to remove ProgressBar when data is loaded
    public void removeLastItem()
    {
        mDataset.remove(mDataset.size() - 1);
        notifyDataSetChanged();
    }

}
