package com.musidroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import l2i013.musidroid.util.*;




public class InstrumentActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrument);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        LinearLayout sv = (LinearLayout) findViewById(R.id.checkboxLayout);

        for (InstrumentName instru : InstrumentName.values()){
            final CheckBox cb = new CheckBox(this);
            cb.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            cb.setText(instru.toString());
            cb.setId(instru.getNum());
            sv.addView(cb);
            cb.isClickable();



            cb.setOnClickListener(this);



        }

    }

    public void preView(View view){
        this.finish();
    }


    @Override
    public void onClick(View v) {


        Intent intent = new Intent(this, OctaveActivity.class);
        startActivity(intent);

    }
}
