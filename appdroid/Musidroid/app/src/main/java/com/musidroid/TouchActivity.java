package com.musidroid;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import model.Global;


public class TouchActivity extends AppCompatActivity {

    TheApplication app;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        SeekBar sk = (SeekBar) findViewById(R.id.seekScroll);

        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Global.offset = i*Global.coeffdep;

                ((TouchBoard)findViewById(R.id.boardSurface)).reDraw();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                System.out.println(Global.offset);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });



    }


    // Offset non remis a 0
    public void onClickExitSU(View view){
        Global.offset = 0;
        finish();
        System.out.println(Global.offset);
    }




}
