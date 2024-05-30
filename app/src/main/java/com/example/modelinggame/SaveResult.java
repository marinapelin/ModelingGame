package com.example.modelinggame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SaveResult extends AppCompatActivity {

    ImageView imageView;
    ImageButton btn_tryagain;
    ImageButton btn_save;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_save);

        btn_tryagain = findViewById(R.id.imgbtn_tryagain);
        btn_tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SaveResult.this, Camera.class);
                startActivity(intent);
            }
        });
        btn_save = findViewById(R.id.imgbtn_yes);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SaveResult.this, ShowResult.class);
                startActivity(intent);
            }
        });
    }


}
