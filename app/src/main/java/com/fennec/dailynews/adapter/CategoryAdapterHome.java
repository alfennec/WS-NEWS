package com.fennec.dailynews.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.fennec.dailynews.R;
import com.fennec.dailynews.entity.Category;
import java.util.List;

public class CategoryAdapterHome extends RecyclerView.Adapter<CategoryAdapterHome.MyViewHolder> {

    public List<Category> list;
    public boolean showAdd = false;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title_category;
        public View divider;
        public View parent;
        public RecyclerView recyclerView;

        public MyViewHolder(View view)
        {
            super(view);
            parent=view;
            title_category = (TextView) view.findViewById(R.id.title_category);
            divider = (View) view.findViewById(R.id.divider_category);
        }
    }

    public CategoryAdapterHome(List<Category> list)
    {
        this.list = list;
    }

    @Override
    public CategoryAdapterHome.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_home, parent, false);

        return new CategoryAdapterHome.MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position)
    {
        if(position == getItemCount()-1 && showAdd)
            return 2;
        return 1;
    }

    @Override
    public void onBindViewHolder(final CategoryAdapterHome.MyViewHolder holder, final int position)
    {
        final Category myCategory = list.get(position);
        holder.title_category.setText(myCategory.name);

        if(position == 0) holder.divider.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 3,1f));
        if(position == 0) holder.divider.setBackgroundColor(Color.rgb(52, 131, 251) );


        Log.d("TAG_GLIDE", "onBindViewHolder: count");

        holder.parent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //CategoryFragment.to_newIntent(myCategory.id);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
