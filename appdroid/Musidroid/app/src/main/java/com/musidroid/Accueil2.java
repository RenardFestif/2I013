package com.musidroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import model.Global;


public class Accueil2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil2);

        Global.fullScreenCall(this);





    }

    public void instrumentChooseView (View view){
        Intent intent = new Intent(this, TempoActivity.class);
        startActivity(intent);
    }

    public void onClickCharger (View view){
        Intent intent = new Intent(this, ChargerActivity.class);
        startActivity(intent);
        ;
    }

}
