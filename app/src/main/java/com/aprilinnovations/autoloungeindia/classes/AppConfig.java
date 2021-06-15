package com.aprilinnovations.autoloungeindia.classes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppConfig {

//    static var baseUrl1 = "https://theautoloungeindia.com/api/v1/auth/"
//    static var baseUrl = "https://theautoloungeindia.com/api/v1/"
     private static String BASE_URL = "https://theautoloungeindia.com/api/v1/";

    public static Retrofit getRetrofit() {

        return new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
