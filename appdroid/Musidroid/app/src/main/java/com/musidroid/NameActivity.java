package com.musidroid;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;

import l2i013.musidroid.util.*;
import model.Global;
import model.extended.PartitionX;



public class NameActivity extends AppCompatActivity{

    public static final String EXTRA_NAME = "com.musidroid.NAME";
    public static final String EXTRA_INSTRU = "com.musidroid.INSTRU";
    public static final String EXTRA_OCTAVE = "com.musidroid.OCTAVE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);



    }

    public void preView(View view){
        this.finish();
    }

    public void sendMessage(View v){

        EditText editText = (EditText) findViewById(R.id.nameEditText);
        String message = editText.getText().toString();


        Intent intentExtra = getIntent();
        String instru = intentExtra.getStringExtra(EXTRA_INSTRU);   //Get InstrumentName
        String octaveStr = intentExtra.getStringExtra(EXTRA_OCTAVE);//Get OctaveStr
        int octave = Integer.parseInt(octaveStr);

        PartitionX p = Global.getPartition();

        InstrumentName instrumentName = null;
        for(InstrumentName i : InstrumentName.values()){
            if (instru.compareToIgnoreCase(i.toString())==0){
                instrumentName = i;
            }
        }
        p.addPart(instrumentName, octave);
        p.setName(p.getSize()-1,message);

        Global.addPartition(p);


        Intent intent = new Intent(this, EditionActivity.class);
        startActivity(intent);
    }

}
