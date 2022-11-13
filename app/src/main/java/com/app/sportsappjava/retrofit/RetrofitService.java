package com.app.sportsappjava.retrofit;


import com.app.sportsappjava.retrofit.Response.ResponseGame;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface RetrofitService {

    @GET("sapk01222019186652.json")
    Call<ResponseGame> getGameData();
    /*
    https://demo.sportz.io/sapk01222019186652.json
        https://demo.sportz.io/nzin01312019187360.json
    * */
}

