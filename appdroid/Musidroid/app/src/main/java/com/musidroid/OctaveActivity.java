package com.musidroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class OctaveActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String EXTRA_INSTRU = "com.musidroid.INSTRU";
    public static final String EXTRA_OCTAVE = "com.musidroid.OCTAVE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_octave);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        RadioButton rb =new RadioButton(this);
        rb.setOnClickListener(this);


    }


    public void preView(View view){
        this.finish();
    }

    @Override
    public void onClick(View v) {


        Intent intent = new Intent(this, NameActivity.class);
        RadioButton rb = (RadioButton) v;
        String octaveStr = (String) rb.getContentDescription();




        Intent intentExtra = getIntent();

        String instru = intentExtra.getStringExtra(InstrumentActivity.EXTRA_INSTRU);    //On recup le nom de l'instru
        intent.putExtra(EXTRA_OCTAVE, octaveStr);                                       //On met l'ocatve dans l'intent
        intent.putExtra(EXTRA_INSTRU, instru);                                          //On remet l'instru dans l'intent

        rb.setChecked(false);


        startActivity(intent);

        finish();


    }

}
