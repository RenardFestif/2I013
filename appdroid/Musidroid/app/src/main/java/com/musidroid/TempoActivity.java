package com.musidroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;

import model.Global;
import model.extended.PartitionX;
import musidroid.Partition;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class TempoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempo);
        //Mis au point du mode fullScreen Immersif
        Global.fullScreenCall(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE

                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }



    public void preView(View view){
        this.finish();
    }

    public void onClick(View view){


        EditText et = findViewById(R.id.tempoEditText);
        String tempostr = et.getText().toString();
        int tempo= Integer.parseInt(tempostr);


        PartitionX newPart = new PartitionX(tempo);   //Nouvelle partition
        Global.addPartition(newPart);

        // On vas vers une autre Activité

        Intent intentNext = new Intent(this, InstrumentActivity.class);
        startActivity(intentNext);

        finish();


    }




}
