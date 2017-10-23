package com.example.admin.moviedbapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        holder.result = r;

        Glide.with( context )
                .load( "https://image.tmdb.org/t/p/w640" + r.getPosterPath() )
                .into( holder.cover );

        holder.title.setText( r.getTitle() );
        holder.releaseDate.setText( r.getReleaseDate().substring( 0, 4) );

//        double score = r.getVoteAverage()*10;
        int score = (int) (r.getVoteAverage()*10);
        holder.rating.setText( score + "% (" + r.getVoteCount() + " votes)" );
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cover;
        TextView title, releaseDate, rating;
        Result result;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: clicked");

                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra( context.getString(R.string.result), result);
                    context.startActivity(intent);
//                    Toast.makeText( context, result.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });

            cover = itemView.findViewById( R.id.ivMovieCover );
            title = itemView.findViewById( R.id.tvMovieTitle );
            releaseDate = itemView.findViewById( R.id.tvMovieReleaseDate );
            rating = itemView.findViewById( R.id.tvMovieRating );
        }
    }
}
