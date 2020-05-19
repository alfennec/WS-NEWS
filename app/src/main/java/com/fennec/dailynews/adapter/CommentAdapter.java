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
import com.fennec.dailynews.config.CommentsJson;
import com.fennec.dailynews.config.TimeAgo;
import com.fennec.dailynews.controller.CommentsActivity;
import com.fennec.dailynews.entity.Comments;
import com.fennec.dailynews.repository.UserRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    public List<Comments> list;
    public boolean showAdd = false;

    public static int selected = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tv_user, tv_date, tv_comment;
        public ImageView image_comment;
        public View parent;
        public RecyclerView recyclerView;

        public MyViewHolder(View view)
        {
            super(view);
            parent=view;
            tv_user = (TextView) view.findViewById(R.id.tv_user);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_comment = (TextView) view.findViewById(R.id.tv_comment);

            image_comment = (ImageView) view.findViewById(R.id.image_comment);

        }
    }

    public CommentAdapter(List<Comments> list)
    {
        this.list = list;
    }

    @Override
    public CommentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comments, parent, false);

        return new CommentAdapter.MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position)
    {
        if(position == getItemCount()-1 && showAdd)
            return 2;
        return 1;
    }

    @Override
    public void onBindViewHolder(final CommentAdapter.MyViewHolder holder, final int position)
    {
        final Comments myComment = list.get(position);

        holder.tv_user.setText(myComment.id_user);
        holder.tv_comment.setText(myComment.message);

        /*** date createrd ago ***/

        String dtStart = myComment.created;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(dtStart);

            TimeAgo tago = new TimeAgo(CommentsActivity.main);
            holder.tv_date.setText(tago.timeAgo(date));
        } catch (ParseException e)
        {
            holder.tv_date.setText(myComment.created);
        }

        Log.d("TAG_GLIDE", "onBindViewHolder: count");

        holder.parent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(myComment.id_user.equals(UserRepository.main_User.name))
                {
                    new SweetAlertDialog(CommentsActivity.main, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure ?")
                            .setContentText(myComment.message)
                            .setConfirmText("Yes,delete it!")
                            .setCancelText("No")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                            {
                                @Override
                                public void onClick(SweetAlertDialog sDialog)
                                {
                                    CommentsJson commentsJson = new CommentsJson("comments/"+myComment.id, CommentsActivity.main, "DELETE", 3, new Comments());

                                    sDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    sDialog.setTitleText("Deleted with success !");
                                    sDialog.setConfirmText("Continue");
                                    sDialog.setCancelText("No");
                                    sDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                                    {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog)
                                        {
                                            sweetAlertDialog.dismissWithAnimation();
                                        }
                                    });
                                }
                            })
                            .show();
                }else
                    {
                        CommentsActivity.add_comment.setText("@"+myComment.id_user+" ");
                    }
            }
        });
    }


    @Override
    public int getItemCount()
    {
        return list.size();
    }
}

