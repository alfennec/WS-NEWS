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
import com.fennec.dailynews.controller.ui.category.CategoryFragment;
import com.fennec.dailynews.entity.Category;
import com.fennec.dailynews.entity.News;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    public List<Category> list;
    public boolean showAdd = false;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title_category;
        public ImageView image_category;
        public View parent;
        public RecyclerView recyclerView;

        public MyViewHolder(View view)
        {
            super(view);
            parent=view;
            title_category = (TextView) view.findViewById(R.id.title_category);
            image_category = (ImageView) view.findViewById(R.id.image_category);
        }
    }

    public CategoryAdapter(List<Category> list)
    {
        this.list = list;
    }

    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);

        return new CategoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position)
    {
        if(position == getItemCount()-1 && showAdd)
            return 2;
        return 1;
    }

    @Override
    public void onBindViewHolder(final CategoryAdapter.MyViewHolder holder, final int position)
    {
        final Category myCategory = list.get(position);
        holder.title_category.setText(myCategory.name);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(20));

        Glide.with(HomeActivity.main).load(Constante.url_host+"/images/category/"+myCategory.image).apply(requestOptions).into(holder.image_category);

        Log.d("TAG_GLIDE", "onBindViewHolder: count");

        holder.parent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                CategoryFragment.to_newIntent(myCategory.id);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
