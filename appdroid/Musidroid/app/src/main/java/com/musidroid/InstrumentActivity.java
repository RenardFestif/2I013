package com.musidroid;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ScrollView;

import l2i013.musidroid.util.*;




public class InstrumentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrument);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        /*ScrollView sv = (ScrollView) findViewById(R.id.scrollInstru);
        CheckBox cb = new CheckBox(getBaseContext());
        for (InstrumentName instru : InstrumentName.values()){

            cb.setText(instru);
            sv.addView(cb);

        }*/


    }
}
