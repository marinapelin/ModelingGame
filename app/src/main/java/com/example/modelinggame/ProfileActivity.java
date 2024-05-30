package com.example.modelinggame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    List<GameItem> completedGamesItemList;
    MyApplication myApplication = (MyApplication) this.getApplication();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static String TAG="Modeling App";
    //private ImageButton profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        //completedGamesItemList= myApplication.getCompletedGameItemList();
//        profile = findViewById(R.id.ib_profile);
//        profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
//                startActivity(intent);
//            }
//        });

//        Log.d(TAG, "onCreate: "+ gameItemList.toString() );
//        //recyclerView.setHasFixedSize(true);
//        recyclerView =findViewById(R.id.lv_itemlist);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        mAdapter = new RecycleViewAdapter(gameItemList, this);
//        recyclerView.setAdapter(mAdapter);
    }

}