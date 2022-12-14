package com.app.sportsappjava.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.sportsappjava.Interface.IMain;
import com.app.sportsappjava.Presenter.MainPresenter;
import com.app.sportsappjava.Utlis.CommonFunctions;
import com.app.sportsappjava.databinding.ActivityMainBinding;
import com.app.sportsappjava.retrofit.Response.ResponseGame;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements IMain.View {
    ActivityMainBinding binding;
    private MainPresenter mainPresenter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainPresenter = new MainPresenter(this);
        mainPresenter.created();
    }

    @Override
    public void init() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        binding.lytTool.imgbck.setVisibility(View.GONE);
        binding.lytTool.toolbrLbl.setText(" Home");
        if (CommonFunctions.isNetworkConnected(MainActivity.this)) {
            mainPresenter.getGameData();
        } else {
            CommonFunctions.showNoInternetDialog(MainActivity.this);
        }

    }

    @Override
    public void onGetResult(ResponseGame responseGame) {

        binding.dateTime.setText(responseGame.getMatchdetail().getMatch().getDate() + " at " + responseGame.getMatchdetail().getMatch().getTime());
        binding.location.setText(responseGame.getMatchdetail().getVenue().getName());
        binding.teamName.setText(responseGame.getTeams().getTeamOne().getNameFull() + " vs " + responseGame.getTeams().getTeamTwo().getNameFull());
        binding.seeMore.setOnClickListener(view -> {
            Gson gson = new Gson();
            Intent intent = new Intent(MainActivity.this, Details_Page.class);
            String myJson = gson.toJson(responseGame);
            intent.putExtra("myjson", myJson);
            startActivity(intent);
        });
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void finishApp() {
        finish();
    }

    @Override
    public void showAppExitInfo() {
        Toast.makeText(this, "Tap Again to exit App", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        mainPresenter.onBackPressed();
    }

}