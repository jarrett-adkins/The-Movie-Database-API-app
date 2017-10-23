package com.example.admin.moviedbapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.moviedbapplication.model.Result;

public class DetailActivity extends AppCompatActivity {

    Result result;
    ImageView poster;
    TextView title, releaseDate, originalLanguage, originalTitle, overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        result = getIntent().getParcelableExtra( getString(R.string.result) );

        poster = findViewById( R.id.ivPoster );
        title = findViewById( R.id.tvMovieTitle );
        releaseDate = findViewById( R.id.tvMovieReleaseDate );
        originalLanguage = findViewById( R.id.tvMovieOriginalLanguage );
        originalTitle = findViewById( R.id.tvMovieOriginalTitle );
        overview = findViewById( R.id.tvMovieOverview );

        Glide.with( this )
                .load( "https://image.tmdb.org/t/p/w640" + result.getPosterPath() )
                .into( poster );

        title.setText( result.getTitle() );
        releaseDate.setText( result.getReleaseDate() );
        originalLanguage.setText( result.getOriginalLanguage() );
        originalTitle.setText( result.getOriginalTitle() );
        overview.setText( result.getOverview() );
    }
}
