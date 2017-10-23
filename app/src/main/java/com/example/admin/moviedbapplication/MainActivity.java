package com.example.admin.moviedbapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.moviedbapplication.data.RetrofitHelper;
import com.example.admin.moviedbapplication.model.MovieDB;
import com.example.admin.moviedbapplication.model.Result;
import com.example.admin.moviedbapplication.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    int page = 1;
    String query = "";

    ImageView logo;
    LinearLayout submitLayout;
    EditText queryView;
    TextView status;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.ItemAnimator itemAnimator;
    MyItemListAdapter myItemListAdapter;
    List<Result> resultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //bind the views
        logo = findViewById( R.id.tvLogo );
        submitLayout = findViewById( R.id.llSubmit );
        queryView = findViewById( R.id.etQuery );
        status = findViewById( R.id.tvStatus );
        recyclerView = findViewById( R.id.rvRecyclerView );

        //set up recycler view
        layoutManager = new LinearLayoutManager(this);
        itemAnimator = new DefaultItemAnimator();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);

        //set a scroll listener to enable lazy loading
        recyclerView.addOnScrollListener( new EndlessRecyclerViewScrollListener((LinearLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                Toast.makeText(MainActivity.this, "onLoadMore", Toast.LENGTH_SHORT).show();
                nextPage();
            }
        });

        queryView.setText( "action" );
    }

    public void buttonPressed(View view) {
        switch ( view.getId() ) {
            case R.id.btnSearch:

//                Log.d(TAG, "buttonPressed: current state:" + findViewById(R.id.llSubmit).getVisibility());
                //visible states are set through ints, not booleans.
                //visible = 0
                //invisible = 4
                //gone = 8

                if( submitLayout.getVisibility() == View.INVISIBLE )
                    submitLayout.setVisibility(View.VISIBLE);
                else
                    submitLayout.setVisibility( View.INVISIBLE );

                break;
            case R.id.btnSubmit:
                searchMovies();
                break;
        }
    }

    public void searchMovies() {
        query = queryView.getText().toString();

        RetrofitHelper.getMovies( queryView.getText().toString(), page )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribeOn(  Schedulers.io() )
                .subscribe(new Observer<MovieDB>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                        status.setText( "Searching...");
                    }

                    @Override
                    public void onNext(@NonNull MovieDB movieDB) {
                        Log.d(TAG, "onNext: ");
//                        Log.d(TAG, "onNext: page:" + movieDB.getPage());
//                        Log.d(TAG, "onNext: total results: " + movieDB.getTotalResults() );
//                        Log.d(TAG, "onNext: get start: " + movieDB.getTotalPages());

                        if( movieDB.getTotalResults() != 0 )
                            resultList = movieDB.getResults();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                        status.setText( "No Results.");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                        Log.d(TAG, "onComplete: total results:" + resultList.size());

                        myItemListAdapter = new MyItemListAdapter( resultList );
                        recyclerView.setAdapter( myItemListAdapter );

                        String s = (resultList.size() > 0) ? "" : "No Results";
                        status.setText( s );
                    }
                });
    }

    public void nextPage() {
        final int positionStart = resultList.size();
        final int itemCount = 20;
        page++;

        RetrofitHelper.getMovies( query, page )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribeOn(  Schedulers.io() )
                .subscribe(new Observer<MovieDB>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                        status.setText( "Searching...");
                    }

                    @Override
                    public void onNext(@NonNull MovieDB movieDB) {
                        Log.d(TAG, "onNext: ");

                        for( Result r : movieDB.getResults() )
                            resultList.add( r );
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                        status.setText( "No Results.");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");

                        myItemListAdapter.notifyItemRangeChanged( positionStart, itemCount );
                    }
                });
    }
}

/*
Screen should contain:
X 1) a search button that reveals a text enter box and a submit button.
X 2) Search the movie DB by movies using the query text entered when the user taps "submit" button.
X 3) Show a list of movies found in the same screen.
4) When scrolled to the bottom of the list, start lazy loading next set of movies and append it to the list.

You get more detail when you search for on movie individually
https://api.themoviedb.org/3/movie/10117?api_key=d5ec8c86179f1ce56a907af60e88e07c
 */