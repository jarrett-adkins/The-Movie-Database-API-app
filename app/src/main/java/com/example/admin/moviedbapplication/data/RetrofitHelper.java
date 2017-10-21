package com.example.admin.moviedbapplication.data;

import com.example.admin.moviedbapplication.model.MovieDB;
import com.example.admin.moviedbapplication.utils.Constants;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class RetrofitHelper {

    public static final String BASE_URL = Constants.URLS.BASE_URL;

    static public Retrofit create(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }

    static public Observable<MovieDB> getMovies( String query, int page ){
        Retrofit retrofit = create();
        RequestService service = retrofit.create(RequestService.class);
        return service.responseService( query, page );
    }

    public interface RequestService{
        @GET( Constants.URLS.REQUEST_URL )
        Observable<MovieDB> responseService( @Query("query") String query, @Query("page") int start );

    }
}
