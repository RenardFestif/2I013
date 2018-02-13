package com.musidroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import java.util.ArrayList;

import model.Global;
import model.extended.PartitionX;


public class EditionActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edition);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        ArrayList<String> list = new ArrayList<>();
        PartitionX p = Global.getPartition();






    }

    public void onClickAdd(View view){
        Intent intent = new Intent(this, InstrumentActivity.class);
        startActivity(intent);


    }

    public void onClickDelete(View view){

    }

    public void onClickEdit(View view){

    }
}
