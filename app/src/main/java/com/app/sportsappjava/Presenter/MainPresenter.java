package com.app.sportsappjava.Presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.app.sportsappjava.Interface.IMain;
import com.app.sportsappjava.retrofit.Response.ResponseGame;
import com.app.sportsappjava.retrofit.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements IMain.Presenter {

    private IMain.View view;
    private long backPressedTime;


    public MainPresenter(IMain.View view) {
        this.view = view;
    }


    @Override
    public void getGameData() {
        Call<ResponseGame> callbackCall = (Call<ResponseGame>) RetrofitBuilder.create().getGameData();
        callbackCall.enqueue(new Callback<ResponseGame>() {

            @Override
            public void onResponse(@NonNull Call<ResponseGame> call, @NonNull Response<ResponseGame> response) {
                view.showLoading();
                ResponseGame resp = response.body();
                if (resp != null) {
                    view.onGetResult(resp);
                    view.hideLoading();
                } else {
                    view.onErrorLoading("Data Not Available");
                    view.hideLoading();
                }
            }

            @Override
            public void onFailure(Call<ResponseGame> call, Throwable t) {
                view.onErrorLoading("Opps! Something went Wrong");
                view.hideLoading();
                Log.e("TAG___error", "onFailure: "+call.toString() );
                Log.e("TAG__error", "onFailure: "+t.toString() );
            }
        });
    }

    @Override
    public void created() {
        view.init();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {

            view.finishApp();
        } else {

            view.showAppExitInfo();
        }
        backPressedTime = System.currentTimeMillis();
    }


}
