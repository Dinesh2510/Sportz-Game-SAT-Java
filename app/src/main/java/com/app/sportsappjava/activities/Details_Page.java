package com.app.sportsappjava.activities;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sportsappjava.Adapter.AdapterClickListener;
import com.app.sportsappjava.Adapter.PlayerAdapter;
import com.app.sportsappjava.R;
import com.app.sportsappjava.Utlis.CommonFunctions;
import com.app.sportsappjava.databinding.ActivityDetailsPageBinding;
import com.app.sportsappjava.databinding.ActivityMainBinding;
import com.app.sportsappjava.retrofit.Response.PlayerDataModelled;
import com.app.sportsappjava.retrofit.Response.ResponseGame;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

public class Details_Page extends AppCompatActivity {
    ActivityDetailsPageBinding binding;
    ResponseGame object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

    }

    public void init() {
        Gson gson = new Gson();
        object = gson.fromJson(getIntent().getStringExtra("myjson"), ResponseGame.class);

        binding.lytTool.imgbck.setOnClickListener(view -> finish());
        binding.lytTool.toolbrLbl.setText(object.getTeams().getTeamOne().getNameShort() + " vs " + object.getTeams().getTeamTwo().getNameShort());
        Gson myGson = new Gson();
        Log.e("TAG_getPlayers", "init: " + myGson.toJson(object.getTeams()));
        Log.e("TAG_getPlayers", "init:__" + myGson.toJson(object.getTeams().getTeamOne()));
        Log.e("TAG_getPlayers", "init___: " + object.getTeams().getTeamOne().getPlayers().values());
        for (String key : object.getTeams().getTeamOne().getPlayers().keySet()) {
            Log.e("TAG_Players_KEY", "Key::" + key);
        }
        for (PlayerDataModelled playerDataModelled : object.getTeams().getTeamOne().getPlayers().values()) {
            Log.e("TAG_Players_VALUES", "Values::" + playerDataModelled.getBatting().getAverage());
        }
        ArrayList<PlayerDataModelled> playerList_teams_one = new ArrayList<PlayerDataModelled>(object.getTeams().getTeamOne().getPlayers().values());
        for (int i = 0; i < playerList_teams_one.size(); i++) {
            Log.e("TAG_PLayers", "List::" + playerList_teams_one.get(i).getNameFull());
            Log.e("TAG_PLayers", "List::" + playerList_teams_one.get(i).getBatting().getRuns());
            Log.e("TAG_PLayers", "List::" + playerList_teams_one.get(i).getBowling().getStyle());
        }

        ArrayList<PlayerDataModelled> playerList_teams_two = new ArrayList<PlayerDataModelled>(object.getTeams().getTeamTwo().getPlayers().values());
        for (int i = 0; i < playerList_teams_two.size(); i++) {
            Log.e("TAG_PLayers", "List::" + playerList_teams_two.get(i).getBatting().getRuns());
            Log.e("TAG_PLayers", "List::" + playerList_teams_two.get(i).getBowling().getStyle());
        }

        //Teams One Data Set
        binding.teamName.setText(object.getTeams().getTeamOne().getNameFull());
        binding.rvTeamOne.setLayoutManager((new LinearLayoutManager(Details_Page.this, LinearLayoutManager.VERTICAL, false)));
        binding.rvTeamOne.setAdapter(new PlayerAdapter(playerList_teams_one, Details_Page.this, new AdapterClickListener() {
            @Override
            public void onItemClick(View view, int pos, Object object) {
                PlayerDataModelled model = (PlayerDataModelled) object;
                showDialogProject(model);
            }
        }));

        //Teams Two Data Set
        binding.teamNameTwo.setText(object.getTeams().getTeamTwo().getNameFull());
        binding.rvTeamTwo.setLayoutManager((new LinearLayoutManager(Details_Page.this, LinearLayoutManager.VERTICAL, false)));
        binding.rvTeamTwo.setAdapter(new PlayerAdapter(playerList_teams_two, Details_Page.this, new AdapterClickListener() {
            @Override
            public void onItemClick(View view, int pos, Object object) {
                PlayerDataModelled model = (PlayerDataModelled) object;
                showDialogProject(model);
            }
        }));


    }

    Dialog dialog_details;

    private void showDialogProject(PlayerDataModelled modelled) {
        dialog_details = new Dialog(this);
        dialog_details.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog_details.setContentView(R.layout.dialog_info);
        dialog_details.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_details.setCancelable(true);

        ((TextView) dialog_details.findViewById(R.id.title)).setText(modelled.getNameFull());
        ((TextView) dialog_details.findViewById(R.id.bating_style)).setText("Batting Style: " + modelled.getBatting().getStyle());
        ((TextView) dialog_details.findViewById(R.id.ball_style)).setText("Bowling Style: " +modelled.getBowling().getStyle());

        (dialog_details.findViewById(R.id.bt_negative)).setOnClickListener(v -> dialog_details.dismiss());
        dialog_details.show();
    }

}