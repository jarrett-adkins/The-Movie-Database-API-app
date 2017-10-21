package com.example.admin.moviedbapplication.utils;

public class Constants {

    public static class URLS {
        public static final String BASE_URL = "https://api.themoviedb.org/";
        public static final String REQUEST_URL = "3/search/movie?language=en-US&include_adult=false&api_key="
                + VALUES.API_KEY;
        public static final String LOGO_SVG_URL = "https://www.themoviedb.org/assets/static_cache/" +
                "02a9430b88975cae16fcfcc9cf7b5799/images/v4/logos/primary-green.svg";
    }

    public static class VALUES {
        public static final String API_KEY = "d5ec8c86179f1ce56a907af60e88e07c";
    }
}
