package com.musidroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class TouchActivity extends AppCompatActivity {

    TheApplication app;

    static Integer page = 1;


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

        TextView tv = findViewById(R.id.numeroPage);
        tv.setText(page.toString());

    }

    public void onClickExitSU(View view){
        finish();
    }

    public void onClickPreviewSU(View view){
        if(page == 1){
            return;
        }
        else{
            page--;
            TextView tv = findViewById(R.id.numeroPage);
            tv.setText(page.toString());
            recreate();
        }
    }

    public void onClickNextSU(View view){

        page++;
        TextView tv = findViewById(R.id.numeroPage);
        tv.setText(page.toString());
        recreate();
    }





}
