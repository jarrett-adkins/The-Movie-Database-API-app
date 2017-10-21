package com.example.admin.moviedbapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.admin.moviedbapplication.model.Result;

import java.util.ArrayList;
import java.util.List;

public class MyItemListAdapter  extends RecyclerView.Adapter<MyItemListAdapter.ViewHolder> {

    private static final String TAG = "MyItemListAdapter";
    Context context;
    List<Result> resultList = new ArrayList<>();

    public MyItemListAdapter(List<Result> resultList) {
        this.resultList = resultList;
    }

    @Override
    public MyItemListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater
                .from( parent.getContext() )
                .inflate( R.layout.list_item, parent, false );

        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(MyItemListAdapter.ViewHolder holder, int position) {
        Result r = resultList.get( position );

        Glide.with( context )
                .load( r.getPosterPath() )
                .centerCrop()
                .placeholder(R.drawable.loading_spinner)
                .into(myImageView);
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cover;

        public ViewHolder(View itemView) {
            super(itemView);

            cover = itemView.findViewById( R.id.ivMovieCover );
        }
    }
}
